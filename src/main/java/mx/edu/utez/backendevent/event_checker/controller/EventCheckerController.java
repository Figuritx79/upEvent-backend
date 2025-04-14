package mx.edu.utez.backendevent.event_checker.controller;

import mx.edu.utez.backendevent.event_checker.model.dto.CheckerAssignDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.event_checker.model.dto.CheckerRegisterDto;
import mx.edu.utez.backendevent.event_checker.service.EventCheckerRegister;
import mx.edu.utez.backendevent.event_checker.service.EventCheckerService;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/checker")
public class EventCheckerController {
	private EventCheckerService eventCheckerService;
	private EventCheckerRegister eventCheckerRegister;

	@Autowired
	public EventCheckerController(EventCheckerService eventCheckerService, EventCheckerRegister eventCheckerRegister) {
		this.eventCheckerService = eventCheckerService;
		this.eventCheckerRegister = eventCheckerRegister;
	}

	@PostMapping("/assigned")
	public ResponseEntity<ResponseObject> assignedEvents(@Valid @RequestBody EmailDto dto) {
		return this.eventCheckerService.assignedEvents(dto);
	}

	@PostMapping("/own")
	public ResponseEntity<ResponseObject> ownCheckers(@Valid @RequestBody EmailDto dto) {
		return this.eventCheckerService.ownChekers(dto);
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseObject> saveChecker(@Valid @RequestBody CheckerRegisterDto dto) {
		return eventCheckerRegister.saveChecker(dto);
	}

	@PostMapping("/assign")
	public ResponseEntity<ResponseObject> assignChecker(@Valid @RequestBody CheckerAssignDto dto) {
		return eventCheckerRegister.assignChecker(dto);
	}

}
