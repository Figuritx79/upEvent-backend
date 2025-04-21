package mx.edu.utez.backendevent.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import mx.edu.utez.backendevent.event.model.dtos.UpdateEventDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.model.dtos.CreateEventDto;
import mx.edu.utez.backendevent.event.service.EventService;
import mx.edu.utez.backendevent.user.model.dto.CreateEventAdminDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;

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

	@PostMapping("/own")
	public ResponseEntity<ResponseObject> findMyOwnEvents(@RequestBody EmailDto dto) {
		if (dto == null) {
			return new ResponseEntity<>(new ResponseObject("No esta el admin", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}
		return service.findMyOwnEvents(dto);
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<ResponseObject> getEventById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@GetMapping("/{name}")
	public ResponseEntity<ResponseObject> getEventByName(@PathVariable String name) {
		return service.findByName(name);
	}

	@PostMapping(value = "/event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> createEvent(
			@RequestPart("eventDto") String eventDtoStr,
			@RequestPart("frontPage") MultipartFile frontPage) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		CreateEventDto eventDto = objectMapper.readValue(eventDtoStr, CreateEventDto.class);

		if (frontPage.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("La imagen es requerida", TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		}

		eventDto.setFrontPage(frontPage);
		return service.save(eventDto);
	}

	@PutMapping(value = "/events-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> updateEvent(
			@RequestPart("eventDto") String eventDtoStr,
			@RequestPart(value = "frontPage", required = false) MultipartFile frontPage) throws JsonProcessingException {

		ObjectMapper objectMapper = new ObjectMapper();
		UpdateEventDto eventDto = objectMapper.readValue(eventDtoStr, UpdateEventDto.class);

		if (frontPage != null && !frontPage.isEmpty()) {
			eventDto.setFrontPage(frontPage);
		}

		return service.update(eventDto);
	}
	@DeleteMapping("/events-delete/{id}")
	public ResponseEntity<ResponseObject> deleteEvent(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
