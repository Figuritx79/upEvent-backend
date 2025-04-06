package mx.edu.utez.backendevent.user.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.user.model.dto.CreateParticipantDto;
import mx.edu.utez.backendevent.user.service.CreateParticipantService;
import mx.edu.utez.backendevent.util.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

	private final CreateParticipantService createParticipantService;

	public ParticipantController(CreateParticipantService createParticipantService) {
		this.createParticipantService = createParticipantService;
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseObject> registerParticipant(
			@Valid @RequestBody CreateParticipantDto participantDto) {
		return createParticipantService.registerParticipant(participantDto);
	}
}
