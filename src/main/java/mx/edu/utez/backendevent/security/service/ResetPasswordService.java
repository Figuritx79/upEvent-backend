package mx.edu.utez.backendevent.security.service;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.passwordResetToken.model.PasswordRestTokenRepository;
import mx.edu.utez.backendevent.passwordResetToken.model.dto.ResetPasswordDto;
import mx.edu.utez.backendevent.passwordResetToken.service.ValidatedTokenUseCase;
import mx.edu.utez.backendevent.security.util.JwtUtil;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Transactional
@Service
public class ResetPasswordService {
	private UserRepository userRepository;
	private PasswordRestTokenRepository passwordRestTokenRepository;
	private JwtUtil util;
	private BCryptPasswordEncoder encoder;
	private ValidatedTokenUseCase validatedToken;

	@Autowired
	public ResetPasswordService(UserRepository userRepository,
			PasswordRestTokenRepository passwordRestTokenRepository, JwtUtil util, BCryptPasswordEncoder encoder,
			ValidatedTokenUseCase validatedTokenUseCase) {
		this.userRepository = userRepository;
		this.passwordRestTokenRepository = passwordRestTokenRepository;
		this.encoder = encoder;
		this.util = util;
		this.validatedToken = validatedTokenUseCase;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> resetPassword(ResetPasswordDto resetPassword) {

		var isTokenValid = validatedToken.validatedUrlToken(resetPassword.getToken());
		if (!isTokenValid) {
			return new ResponseEntity<>(new ResponseObject("Token invalido", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
		}
		var idUser = UUID.fromString(util.extractUsername(resetPassword.getToken()));
		Optional<User> user = userRepository.findById(idUser);
		if (!user.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Usuario no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		var newPasswordUser = user.get();
		newPasswordUser.setPassword(encoder.encode(resetPassword.getPassword()));
		userRepository.save(newPasswordUser);

		var recordPasswordResetToken = passwordRestTokenRepository.findByToken(resetPassword.getToken());
		if (recordPasswordResetToken.isPresent()) {
			passwordRestTokenRepository.deleteById(recordPasswordResetToken.get().getId());
		}

		return new ResponseEntity<>(new ResponseObject("Restablecimiento Exitoso", TypeResponse.SUCCESS),
				HttpStatus.OK);

	}

}
