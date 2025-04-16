package mx.edu.utez.backendevent.workshop.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.landingPage.model.dtos.CreateLandingPageDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.workshop.model.WorkshopDto;
import mx.edu.utez.backendevent.workshop.model.WorkshopRepository;
import mx.edu.utez.backendevent.workshop.model.dtos.CreateWorkShopDto;
import mx.edu.utez.backendevent.workshop.service.WorkshopService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

@RestController
@RequestMapping("/api/workshop")
public class WorkshopController {

	private final WorkshopRepository workshopRepository;

	private final WorkshopService service;

	public WorkshopController(WorkshopService service, WorkshopRepository workshopRepository) {
		this.service = service;
		this.workshopRepository = workshopRepository;
	}

	@GetMapping("/workshops")
	public ResponseEntity<ResponseObject> getAllWorkshops() {
		return service.findAll();
	}

	@GetMapping("/workshop/{id}")
	public ResponseEntity<ResponseObject> getWorkshopById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@GetMapping("/event/{eventId}")
	public ResponseEntity<ResponseObject> findByEventId(@PathVariable String eventId) {
		var idUuid = UUID.fromString(eventId);
		return service.findByEvent(idUuid);
	}

	@PostMapping(value = "/workshops/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> saveWorkShop(@RequestPart("workshop") String workshop,
			@RequestPart("speakerImage") MultipartFile speakerImage,
			@RequestPart("workshopImage") MultipartFile worshopImage) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			var workshopDto = objectMapper.readValue(workshop, CreateWorkShopDto.class);
			workshopDto.setSpeakerImage(speakerImage);
			workshopDto.setWorkshopImage(worshopImage);
			return service.saveWorkShop(workshopDto);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject("Error al procesar la solicitud", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/status")
	public ResponseEntity<ResponseObject> changeStatus(@Valid @RequestBody UUID id) {
		return service.changeStatus(id);
	}

	// este eliminar es un eliminar fisico asi que hay que tener cuidado
	@DeleteMapping("/workshop-delete/{id}")
	public ResponseEntity<ResponseObject> deleteWorkshop(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
