package mx.edu.utez.backendevent.user.controller;

import mx.edu.utez.backendevent.user.model.dto.UpdatePasswordDto;
import mx.edu.utez.backendevent.user.model.dto.UpdateUserDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.UserWorkShopRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import mx.edu.utez.backendevent.user.model.dto.CreateEventAdminDto;
import mx.edu.utez.backendevent.user.model.dto.UpdateMovilUserDto;
import mx.edu.utez.backendevent.user.service.CreateAdminEvent;
import mx.edu.utez.backendevent.user.service.UserService;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;

@RestController
@RequestMapping("/api/user")
public class UserController {
	private final UserWorkShopRegistrationRepository userWorkShopRegistrationRepository;
	private UserService service;
	private CreateAdminEvent createAdminEvent;

	@Autowired
	public UserController(UserService service, CreateAdminEvent createAdminEvent, UserWorkShopRegistrationRepository userWorkShopRegistrationRepository) {
		this.service = service;
		this.createAdminEvent = createAdminEvent;
		this.userWorkShopRegistrationRepository = userWorkShopRegistrationRepository;
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

	@PatchMapping("/status")
	public ResponseEntity<ResponseObject> toggleStatusByEmail(@Valid @RequestBody EmailDto dto) {
		return service.toggleStatusByEmail(dto);
	}

	@PatchMapping("/update-password")
	public ResponseEntity<ResponseObject> updatePassword(@Valid @RequestBody UpdatePasswordDto dto) {
		return service.updatePassword(dto);
	}

	@PatchMapping("/update-profile")
	public ResponseEntity<ResponseObject> updateProfile(
			@Valid @RequestBody UpdateUserDto dto
	) {
		return service.updateProfile(dto);
	}

}
