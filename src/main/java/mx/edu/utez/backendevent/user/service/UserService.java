package mx.edu.utez.backendevent.user.service;

import java.sql.SQLException;
import java.util.Optional;

import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.dto.UpdatePasswordDto;
import mx.edu.utez.backendevent.user.model.dto.UpdateUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.backendevent.security.service.AuthService;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.user.model.dto.UpdateMovilUserDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class UserService {

	private final AuthService authService;
	private Logger log = LoggerFactory.getLogger(UserService.class);

	@ReadOnlyProperty
	private UserRepository repository;

	private PasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository repository, AuthService authService, PasswordEncoder encoder) {
		this.repository = repository;
		this.authService = authService;
		this.encoder = encoder;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		var user = repository.findAll();
		log.info("Todos los usuarios");
		return new ResponseEntity<>(
				new ResponseObject("All users, doesnt matter the role ", user, TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAdminEvent() {
		var eventAdmins = repository.findAdminEvent();
		log.info("Admin eventoss");
		return new ResponseEntity<>(new ResponseObject("Event Admins", eventAdmins, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findNormalUser() {
		var normalUsers = repository.findNormal();
		log.info("Usuairos normales");
		return new ResponseEntity<>(new ResponseObject("Normal Users", normalUsers, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findChecker() {
		var checker = repository.findCheckers();
		log.info("Cheacdores");
		return new ResponseEntity<>(new ResponseObject("Checkers", checker, TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> profile(EmailDto dto) {
		var existUser = repository.findByEmail(dto.getEmail());
		if (!existUser.isPresent()) {
			log.info("No existe el usuario" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("No se enctro la informacion", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		log.info("Existe el usuario" + dto.getEmail());
		return new ResponseEntity<>(new ResponseObject("Profile", existUser.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> updateInfo(UpdateMovilUserDto dto) {
		var existeUser = repository.findByEmail(dto.getEmail());

		if (!existeUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Usuario no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var user = existeUser.get();

		if (dto.getPassword() == null && dto.getPhone() == null) {
			return new ResponseEntity<>(new ResponseObject("No hay datos quea actualizar", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}

		if (dto.getPhone() != null) {
			user.setPhone(dto.getPhone());
		}
		if (dto.getPassword() != null) {
			user.setPassword(encoder.encode(dto.getPassword()));
		}

		repository.save(user);

		return new ResponseEntity<>(new ResponseObject("Usuario actualizado correctamente", TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> toggleStatusByEmail(EmailDto dto) {
		Optional<User> optionalUser = repository.findByEmail(dto.getEmail());
		if (!optionalUser.isPresent()) {
			log.info("Usuario no encontrado con correo: {}", dto.getEmail());
			return new ResponseEntity<>(
					new ResponseObject("Usuario no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		User user = optionalUser.get();
		user.setStatus(!user.isStatus());
		repository.save(user);
		log.info("Estado del usuario {} cambiado a {}", dto.getEmail(), user.isStatus());
		return new ResponseEntity<>(
				new ResponseObject("Estado actualizado correctamente", TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(rollbackFor = {SQLException.class})
	public ResponseEntity<ResponseObject> updatePassword(UpdatePasswordDto dto) {
		Optional<User> optionalUser = repository.findByEmail(dto.getEmail());
		if (!optionalUser.isPresent()) {
			log.warn("Usuario no encontrado con email: {}", dto.getEmail());
			return new ResponseEntity<>(
					new ResponseObject("Usuario no encontrado", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND
			);
		}

		User user = optionalUser.get();

		if (!encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
			log.warn("Contraseña actual incorrecta para usuario: {}", dto.getEmail());
			return new ResponseEntity<>(
					new ResponseObject("Contraseña actual incorrecta", TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		}

		user.setPassword(encoder.encode(dto.getNewPassword()));
		repository.save(user);

		log.info("Contraseña actualizada para usuario: {}", dto.getEmail());
		return new ResponseEntity<>(
				new ResponseObject("Contraseña actualizada exitosamente", TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(rollbackFor = {SQLException.class})
	public ResponseEntity<ResponseObject> updateProfile(UpdateUserDto dto) {
		Optional<User> optionalUser = repository.findByEmail(dto.getCurrentEmail());
		if (!optionalUser.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Usuario no encontrado", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND
			);
		}

		User user = optionalUser.get();

		if (dto.getNewEmail() != null && !dto.getNewEmail().isEmpty()) {
			user.setEmail(dto.getNewEmail());
		}
		if (dto.getName() != null && !dto.getName().isEmpty()) {
			user.setName(dto.getName());
		}
		if (dto.getLastname() != null && !dto.getLastname().isEmpty()) {
			user.setLastname(dto.getLastname());
		}
		if (dto.getPhone() != null && !dto.getPhone().isEmpty()) {
			user.setPhone(dto.getPhone());
		}
		if(dto.getCompanyName() != null && !dto.getCompanyName().isEmpty()) {
			user.setCompanyName(dto.getCompanyName());
		}

		repository.saveAndFlush(user);

		return new ResponseEntity<>(
				new ResponseObject("Perfil actualizado exitosamente", user, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}



}
