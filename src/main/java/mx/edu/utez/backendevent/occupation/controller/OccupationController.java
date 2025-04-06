package mx.edu.utez.backendevent.occupation.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.occupation.model.OccupationDto;
import mx.edu.utez.backendevent.occupation.service.OccupationService;
import mx.edu.utez.backendevent.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/occupation")
public class OccupationController {
	private final OccupationService service;

	@Autowired
	public OccupationController(OccupationService service) {
		this.service = service;
	}

	@PostMapping("/")
	public ResponseEntity<ResponseObject> createOccupation(
			@Valid @RequestBody OccupationDto occupationDto) {
		return service.createOccupation(occupationDto);
	}

	@GetMapping("/")
	public ResponseEntity<ResponseObject> getAllOccupations() {
		return service.getAllOccupations();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseObject> getOccupationById(@PathVariable Long id) {
		return service.getOccupationById(id);
	}
}
