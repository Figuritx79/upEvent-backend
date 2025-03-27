package mx.edu.utez.backendevent.event.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {

	private final EventService service;

	public EventController(EventService service) {
		this.service = service;
	}

	@GetMapping("/events")
	public ResponseEntity<ResponseObject> getAllEvents() {
		return service.findAll();
	}

	@GetMapping("/events/{id}")
	public ResponseEntity<ResponseObject> getEventById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PostMapping("/events-create")
	public ResponseEntity<ResponseObject> createEvent(@Valid @RequestBody EventDto eventDto) {
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
