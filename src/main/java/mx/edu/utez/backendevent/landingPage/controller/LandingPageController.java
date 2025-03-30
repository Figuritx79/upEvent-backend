package mx.edu.utez.backendevent.landingPage.controller;

import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.landingPage.model.dtos.CreateLandingPageDto;
import mx.edu.utez.backendevent.landingPage.service.LandingPageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/landing-page")
public class LandingPageController {

	private final LandingPageService service;

	public LandingPageController(LandingPageService service) {
		this.service = service;
	}

	@GetMapping("/landing")
	public ResponseEntity<ResponseObject> getAllLandingPages() {
		return service.findAll();
	}

	@GetMapping("/landing/{slug}")
	public ResponseEntity<ResponseObject> findBySlug(@PathVariable(name = "slug") String slug) {
		return service.findBySlug(slug);
	}

	@PostMapping(value = "/landing/create/{eventName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> saveLanding(@PathVariable String eventName,
			@RequestPart("logo") MultipartFile logo, @RequestPart("gallery1") MultipartFile gallery1,
			@RequestPart("gallery2") MultipartFile gallery2, @RequestPart("gallery3") MultipartFile gallery3) {

		try {

			var originalNameEvent = eventName.replace("-", " ");
			CreateLandingPageDto landingPageDto = new CreateLandingPageDto();
			landingPageDto.setGallery1(gallery1);
			landingPageDto.setGallery2(gallery2);
			landingPageDto.setGallery3(gallery3);
			landingPageDto.setLogo(logo);
			landingPageDto.setEventName(originalNameEvent);
			return service.saveLanding(landingPageDto);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject("Error al procesar la solicitud", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @PostMapping("/landing-page/create")
	// public ResponseEntity<ResponseObject> createLandingPage(@Valid @RequestBody
	// LandingPageDto landingPageDto) {
	// return service.save(landingPageDto);
	// }

	// @PutMapping("/landing-page/update")
	// public ResponseEntity<ResponseObject> updateLandingPage(@Valid @RequestBody
	// LandingPageDto landingPageDto) {
	// return service.update(landingPageDto);
	// }

	@DeleteMapping("/landing/delete/{id}")
	public ResponseEntity<ResponseObject> deleteLandingPage(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
