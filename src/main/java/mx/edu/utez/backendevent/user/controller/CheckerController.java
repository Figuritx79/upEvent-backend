package mx.edu.utez.backendevent.user.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.user.model.dto.CreateCheckerDto;
import mx.edu.utez.backendevent.user.service.CreateCheckerService;
import mx.edu.utez.backendevent.util.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checker")
public class CheckerController {

	private final CreateCheckerService createCheckerService;

	public CheckerController(CreateCheckerService createCheckerService) {
		this.createCheckerService = createCheckerService;
	}

	@PostMapping("/register")
	public ResponseEntity<ResponseObject> registerChecker(
			@Valid @RequestBody CreateCheckerDto checkerDto) {
		return createCheckerService.registerChecker(checkerDto);
	}
}
