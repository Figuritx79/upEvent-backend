package mx.edu.utez.backendevent.userEventRegistration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserDto;
import mx.edu.utez.backendevent.userEventRegistration.service.RegisterEventService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/registration")
public class UserEventRegistrationController {
	private RegisterEventService registerEventService;

	@Autowired
	public UserEventRegistrationController(RegisterEventService registerEventService) {
		this.registerEventService = registerEventService;
	}

	@PostMapping("/event-register")
	@PermitAll
	public ResponseEntity<ResponseObject> saveRegistration(@RequestBody RegisterEventUserDto dto) {
		return registerEventService.registerUserEvent(dto);
	}

}
