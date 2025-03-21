package mx.edu.utez.backendevent.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.security.dto.AuthRequest;
import mx.edu.utez.backendevent.security.service.AuthService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
	private AuthService service;

	@Autowired
	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseObject> login(@RequestBody AuthRequest authRequest) {
		return service.login(authRequest);
	}

}
