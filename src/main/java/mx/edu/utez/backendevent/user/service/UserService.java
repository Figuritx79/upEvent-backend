package mx.edu.utez.backendevent.user.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.backendevent.security.controller.AuthController;
import mx.edu.utez.backendevent.security.service.AuthService;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class UserService {

	private final AuthService authService;

	private final AuthController authController;

	@ReadOnlyProperty
	private UserRepository repository;

	public UserService(UserRepository repository, AuthController authController, AuthService authService) {
		this.repository = repository;
		this.authController = authController;
		this.authService = authService;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		var user = repository.findAll();
		return new ResponseEntity<>(
				new ResponseObject("All users, doesnt matter the role ", user, TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAdminEvent() {
		var eventAdmins = repository.findAdminEvent();

		return new ResponseEntity<>(new ResponseObject("Event Admins", eventAdmins, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findNormalUser() {
		var normalUsers = repository.findNormal();
		return new ResponseEntity<>(new ResponseObject("Normal Users", normalUsers, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findChecker() {
		var checker = repository.findCheckers();
		return new ResponseEntity<>(new ResponseObject("Checkers", checker, TypeResponse.SUCCESS), HttpStatus.OK);
	}

}
