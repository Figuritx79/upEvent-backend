package mx.edu.utez.backendevent.security.service;

import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;
import mx.edu.utez.backendevent.security.UserDetailsServiceImpl;
import mx.edu.utez.backendevent.security.dto.AuthRequest;
import mx.edu.utez.backendevent.security.dto.RecoveryPasswordRequest;
import mx.edu.utez.backendevent.security.util.JwtUtil;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.util.EmailSender;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;

@Service
@Transactional
public class AuthService {

	private final AuthenticationManager authenticationManager;

	private final UserDetailsServiceImpl userDetailsService;

	private final JwtUtil jwtUtil;

	private EmailSender emailSender;

	private BCryptPasswordEncoder encoder;

	private Logger log = LoggerFactory.getLogger(AuthService.class);

	private UserRepository repository;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService,
			JwtUtil jwtUtil, BCryptPasswordEncoder encoder, UserRepository repository, EmailSender emailSender) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.jwtUtil = jwtUtil;
		this.encoder = encoder;
		this.emailSender = emailSender;
		this.repository = repository;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> login(AuthRequest authRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
		} catch (BadCredentialsException badCredentialsException) {
			log.warn(badCredentialsException.getMessage());
			log.warn("Autenticacion fallida del usuario " + authRequest.getEmail() + " " + authRequest.getPassword());

			return ResponseEntity.status(401).body(new ResponseObject("Error con tus credenciaes", TypeResponse.ERROR));
		}
		var jwt = jwtUtil.generateToken(userDetailsService.loadUserByUsername(authRequest.getEmail()));

		User user = repository.findByEmail(authRequest.getEmail())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!encoder.matches(authRequest.getPassword(), user.getPassword())) {
			log.warn("El usuario" + user.getEmail() + "ingreso con la contra equivocada");
			return new ResponseEntity<>(new ResponseObject("Fallo al autenticar", TypeResponse.ERROR),
					HttpStatus.UNAUTHORIZED);
		}

		var headers = new HttpHeaders();
		var mapResponse = new HashMap<String, String>();

		ResponseCookie cookie = ResponseCookie.from("access_token", jwt)
				.httpOnly(true)
				.secure(false)
				.maxAge(86400)
				.sameSite("LAX")
				.path("/")
				.build();

		mapResponse.put("role", user.getRole().getName());
		log.info("Inicio de sesion exitoso" + user.getEmail());
		var response = new ResponseObject("Autenticado", mapResponse, TypeResponse.SUCCESS);
		headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
		headers.add("Authorization", "Bearer " + jwt);
		return ResponseEntity.ok().headers(headers).body(response);

	}


}
