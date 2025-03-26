package mx.edu.utez.backendevent.workshop.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.workshop.model.WorkshopDto;
import mx.edu.utez.backendevent.workshop.service.WorkshopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/workshop")
public class WorkshopController {

	private final WorkshopService service;

	public WorkshopController(WorkshopService service) {
		this.service = service;
	}

	@GetMapping("/workshops")
	public ResponseEntity<ResponseObject> getAllWorkshops() {
		return service.findAll();
	}

	@GetMapping("/workshop/{id}")
	public ResponseEntity<ResponseObject> getWorkshopById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PostMapping("/workshop-create")
	public ResponseEntity<ResponseObject> createWorkshop(@Valid @RequestBody WorkshopDto workshopDto) {
		return service.save(workshopDto);
	}

	@PutMapping("/workshop-update")
	public ResponseEntity<ResponseObject> updateWorkshop(@Valid @RequestBody WorkshopDto workshopDto) {
		return service.update(workshopDto);
	}
	//este eliminar es un eliminar fisico asi que hay que tener cuidado
	@DeleteMapping("/workshop-delete/{id}")
	public ResponseEntity<ResponseObject> deleteWorkshop(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
