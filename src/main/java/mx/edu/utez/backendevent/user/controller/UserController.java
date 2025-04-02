package mx.edu.utez.backendevent.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import mx.edu.utez.backendevent.user.model.dto.CreateEventAdminDto;
import mx.edu.utez.backendevent.user.model.dto.UpdateMovilUserDto;
import mx.edu.utez.backendevent.user.service.CreateAdminEvent;
import mx.edu.utez.backendevent.user.service.UserService;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private UserService service;
	private CreateAdminEvent createAdminEvent;

	@Autowired
	public UserController(UserService service, CreateAdminEvent createAdminEvent) {
		this.service = service;
		this.createAdminEvent = createAdminEvent;
	}

	@GetMapping("/users")
	public ResponseEntity<ResponseObject> getAllUser() {
		return service.findAll();
	}

	@GetMapping("/event-admins")
	public ResponseEntity<ResponseObject> findEventAdmin() {
		return service.findAdminEvent();
	}

	@GetMapping("/normal-users")
	public ResponseEntity<ResponseObject> findNormalUser() {
		return service.findNormalUser();
	}

	@GetMapping("/checkers")
	public ResponseEntity<ResponseObject> findCheckerResponse() {
		return service.findChecker();
	}

	@PostMapping("/register-admin-event")
	@PermitAll
	public ResponseEntity<ResponseObject> registerAdminEvent(@Valid @RequestBody CreateEventAdminDto adminDto) {
		return createAdminEvent.registerAdmin(adminDto);
	}

	@PostMapping("/profile")
	public ResponseEntity<ResponseObject> profile(@RequestBody EmailDto dto) {
		return service.profile(dto);
	}

	@PatchMapping("/update")
	public ResponseEntity<ResponseObject> updateMoviInfo(@Valid @RequestBody UpdateMovilUserDto dto) {
		return service.updateInfo(dto);
	}

}
