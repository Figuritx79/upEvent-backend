package mx.edu.utez.backendevent.landingPage.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.landingPage.model.LandingPageDto;
import mx.edu.utez.backendevent.landingPage.service.LandingPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/landing-page")
public class LandingPageController {

	private final LandingPageService service;

	public LandingPageController(LandingPageService service) {
		this.service = service;
	}

	@GetMapping("/landing-pages")
	public ResponseEntity<ResponseObject> getAllLandingPages() {
		return service.findAll();
	}

	@GetMapping("/landing-pages/{id}")
	public ResponseEntity<ResponseObject> getLandingPageById(@PathVariable UUID id) {
		return service.findById(id);
	}

	@PostMapping("/landing-page/create")
	public ResponseEntity<ResponseObject> createLandingPage(@Valid @RequestBody LandingPageDto landingPageDto) {
		return service.save(landingPageDto);
	}

	@PutMapping("/landing-page/update")
	public ResponseEntity<ResponseObject> updateLandingPage(@Valid @RequestBody LandingPageDto landingPageDto) {
		return service.update(landingPageDto);
	}

	@DeleteMapping("/landing-page/delete/{id}")
	public ResponseEntity<ResponseObject> deleteLandingPage(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
