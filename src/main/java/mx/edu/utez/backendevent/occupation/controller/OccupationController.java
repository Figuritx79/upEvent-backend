package mx.edu.utez.backendevent.occupation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.occupation.service.OccupationService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/occupation")
public class OccupationController {
	private OccupationService service;

	@Autowired
	public OccupationController(OccupationService service) {
		this.service = service;
	}

	@GetMapping("/occupations")
	public ResponseEntity<ResponseObject> allOccupations() {
		return service.allOccupations();
	}

}
