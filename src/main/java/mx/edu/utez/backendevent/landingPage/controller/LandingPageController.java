package mx.edu.utez.backendevent.landingPage.controller;

import mx.edu.utez.backendevent.landingPage.model.dtos.UpdateLandingPageDto;
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

	@GetMapping("/landing/event/{id}")
	public ResponseEntity<ResponseObject> getLandingByEvent(@PathVariable UUID id) {
		return service.findByEvent(id);
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

	@PutMapping(value = "/landing/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResponseObject> updateLanding(
			@RequestPart("id") String id,
			@RequestPart(value = "logo", required = false) MultipartFile logo,
			@RequestPart(value = "gallery1", required = false) MultipartFile gallery1,
			@RequestPart(value = "gallery2", required = false) MultipartFile gallery2,
			@RequestPart(value = "gallery3", required = false) MultipartFile gallery3,
			@RequestPart("eventName") String eventName) {

		UpdateLandingPageDto landingPageDto = new UpdateLandingPageDto();
		landingPageDto.setId(id);
		landingPageDto.setLogo(logo);
		landingPageDto.setGallery1(gallery1);
		landingPageDto.setGallery2(gallery2);
		landingPageDto.setGallery3(gallery3);
		landingPageDto.setEventName(eventName.replace("-", " "));

		return service.updateLanding(landingPageDto);
	}

	@DeleteMapping("/landing/delete/{id}")
	public ResponseEntity<ResponseObject> deleteLandingPage(@PathVariable UUID id) {
		return service.deleteById(id);
	}
}
