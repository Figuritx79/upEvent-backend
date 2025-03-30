package mx.edu.utez.backendevent.event.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.model.dtos.CreateEventDto;
import mx.edu.utez.backendevent.event.service.EventService;
import mx.edu.utez.backendevent.user.model.dto.CreateEventAdminDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/event")
public class EventController {

	private final EventService service;

	public EventController(EventService service) {
		this.service = service;
	}

	@GetMapping("/events")
	public ResponseEntity<ResponseObject> getAllEvents() {
		return service.findAll();
	}

	@GetMapping("/own/{admin}")
	public ResponseEntity<ResponseObject> findMyOwnEvents(@PathVariable String admin) {
		if (admin.isEmpty()) {
			return new ResponseEntity<>(new ResponseObject("No esta el admin", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}
		var id = UUID.fromString(admin);
		return service.findMyOwnEvents(id);
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<ResponseObject> getEventById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PostMapping(value = "/event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> createEvet(@RequestPart("eventDto") String eventDtoStr,
			@RequestPart("frontPage") MultipartFile frontPage) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		CreateEventDto eventDto = objectMapper.readValue(eventDtoStr, CreateEventDto.class);
		if (frontPage != null) {
			eventDto.setFrontPage(frontPage);
		}
		return service.save(eventDto);
	}

	@PutMapping("/events-update")
	public ResponseEntity<ResponseObject> updateEvent(@Valid @RequestBody EventDto eventDto) {
		return service.update(eventDto);
	}

	// Este eliminar tambien es un eliminar fisico
	@DeleteMapping("/events-delete/{id}")
	public ResponseEntity<ResponseObject> deleteEvent(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
