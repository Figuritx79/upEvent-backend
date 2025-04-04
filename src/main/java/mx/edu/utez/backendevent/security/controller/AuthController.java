package mx.edu.utez.backendevent.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.passwordResetToken.model.dto.ResetPasswordDto;
import mx.edu.utez.backendevent.security.dto.AuthRequest;
import mx.edu.utez.backendevent.security.dto.RecoveryPasswordRequest;
import mx.edu.utez.backendevent.security.service.AuthService;
import mx.edu.utez.backendevent.security.service.RecoveryPasswordUseCase;
import mx.edu.utez.backendevent.security.service.ResetPasswordService;
import mx.edu.utez.backendevent.util.ResponseObject;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
public class 	AuthController {
	private AuthService service;
	private RecoveryPasswordUseCase passwordUseCase;
	private ResetPasswordService resetPasswordService;

	@Autowired
	public AuthController(AuthService service, RecoveryPasswordUseCase passwordUseCase,
			ResetPasswordService resetPasswordService) {
		this.service = service;
		this.resetPasswordService = resetPasswordService;
		this.passwordUseCase = passwordUseCase;
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseObject> login(@RequestBody AuthRequest authRequest) {
		return service.login(authRequest);
	}

	@PostMapping("/recovery-password")
	public ResponseEntity<ResponseObject> recoveryPassword(
			@Validated @RequestBody RecoveryPasswordRequest passwordRequest) {
		return passwordUseCase.recoveryPassword(passwordRequest);
	}

	@PatchMapping("/reset-password")
	public ResponseEntity<ResponseObject> resetPassword(@Validated @RequestBody ResetPasswordDto resetPasswordDto) {
		return resetPasswordService.resetPassword(resetPasswordDto);
	}

}
