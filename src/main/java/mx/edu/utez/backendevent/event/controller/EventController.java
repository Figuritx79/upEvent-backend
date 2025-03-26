package mx.edu.utez.backendevent.event.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {

	private final EventService service;

	public EventController(EventService service) {
		this.service = service;
	}

	@GetMapping("/")
	public ResponseEntity<ResponseObject> getAllEvents() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getEventById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PostMapping("/")
	public ResponseEntity<ResponseObject> createEvent(@Valid @RequestBody EventDto eventDto) {
		return service.save(eventDto);
	}

	@PutMapping("/")
	public ResponseEntity<ResponseObject> updateEvent(@Valid @RequestBody EventDto eventDto) {
		return service.update(eventDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseObject> deleteEvent(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
