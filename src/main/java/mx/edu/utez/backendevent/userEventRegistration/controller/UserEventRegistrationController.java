package mx.edu.utez.backendevent.userEventRegistration.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.security.PermitAll;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserMovilDto;
import mx.edu.utez.backendevent.userEventRegistration.service.RegisterEventService;
import mx.edu.utez.backendevent.userEventRegistration.service.UserEventRegistratationService;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.CreateUserWorkshopDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.service.UserWorshopRegister;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/registration")
public class UserEventRegistrationController {
	private RegisterEventService registerEventService;
	private UserEventRegistratationService registratationService;
	private UserWorshopRegister registerWorkshopService;

	@Autowired
	public UserEventRegistrationController(RegisterEventService registerEventService,
			UserEventRegistratationService registratationService, UserWorshopRegister registerWorkshopService) {
		this.registerEventService = registerEventService;
		this.registratationService = registratationService;
		this.registerWorkshopService = registerWorkshopService;
	}

	@PostMapping("/event-register")
	@PermitAll
	public ResponseEntity<ResponseObject> saveRegistration(@RequestBody RegisterEventUserDto dto) {
		return registerEventService.registerUserEvent(dto);
	}

	@PostMapping("/event-register/movil")
	public ResponseEntity<ResponseObject> saveRegistrationMovil(@RequestBody RegisterEventUserMovilDto dto) {
		return registerEventService.registerUserEventMovil(dto);
	}

	@PostMapping("/own")
	public ResponseEntity<ResponseObject> registeredEventUser(@RequestBody EmailDto email) {
		return registratationService.eventRegistered(email.getEmail());
	}

	@PostMapping("/workshop-register")
	public ResponseEntity<ResponseObject> registerWorkShopUser(@RequestBody CreateUserWorkshopDto dto) {
		return registerWorkshopService.saveRegistrationWorkShop(dto);
	}

	@GetMapping("/participants/{idEvent}")
	public ResponseEntity<ResponseObject> findParticipant(@PathVariable UUID idEvent) {
		return registratationService.findParticipantByEvent(idEvent);
	}

}
