package mx.edu.utez.backendevent.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.user.service.UserService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {
	private UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@GetMapping("/users")
	public ResponseEntity<ResponseObject> getAllUser() {
		return service.getAllUser();
	}

}
