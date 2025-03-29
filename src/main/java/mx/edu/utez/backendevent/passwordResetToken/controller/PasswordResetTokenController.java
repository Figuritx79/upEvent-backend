package mx.edu.utez.backendevent.passwordResetToken.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mx.edu.utez.backendevent.passwordResetToken.model.dto.PasswordResetTokenDto;
import mx.edu.utez.backendevent.passwordResetToken.service.ValidatedTokenUseCase;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/token")
public class PasswordResetTokenController {
	private ValidatedTokenUseCase validTokenUseCase;

	@Autowired
	public PasswordResetTokenController(ValidatedTokenUseCase validatedTokenUseCase) {
		this.validTokenUseCase = validatedTokenUseCase;
	}

	@PostMapping("/valid")
	public ResponseEntity<ResponseObject> validToken(@Valid @RequestBody PasswordResetTokenDto token) {
		return validTokenUseCase.validatedUrlToken(token);
	}

}
