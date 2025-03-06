package mx.edu.utez.backendevent.gender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.gender.service.GenderService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/gender")
public class GenderController {

	private GenderService genderService;

	@Autowired
	public GenderController(GenderService genderService) {
		this.genderService = genderService;
	}

	@GetMapping("/genders")
	public ResponseEntity<ResponseObject> getGenders() {
		return genderService.getGenders();
	}

}
