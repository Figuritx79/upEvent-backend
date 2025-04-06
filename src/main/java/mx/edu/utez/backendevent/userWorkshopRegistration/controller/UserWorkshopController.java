package mx.edu.utez.backendevent.userWorkshopRegistration.controller;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EventDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.UserWorkshopsByEmailDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.service.UserWorshopRegister;
import mx.edu.utez.backendevent.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user-workshops")
public class UserWorkshopController {

	private final UserWorshopRegister userWorshopRegister;

	@Autowired
	public UserWorkshopController(UserWorshopRegister userWorshopRegister) {
		this.userWorshopRegister = userWorshopRegister;
	}

	@PostMapping("/my-workshops")
	public ResponseEntity<ResponseObject> getMyWorkshops(
			@Valid @RequestBody EventDto dto
	) {
		return userWorshopRegister.getWorkshopsByUserEmail(dto.getEmail(), dto.getIdEvent());
	}

}
