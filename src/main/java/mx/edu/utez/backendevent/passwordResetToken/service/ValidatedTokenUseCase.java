package mx.edu.utez.backendevent.passwordResetToken.service;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.passwordResetToken.model.PasswordRestTokenRepository;
import mx.edu.utez.backendevent.passwordResetToken.model.dto.PasswordResetTokenDto;
import mx.edu.utez.backendevent.security.util.JwtUtil;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class ValidatedTokenUseCase {
	private JwtUtil util;
	private PasswordRestTokenRepository repository;
	private Logger log = LoggerFactory.getLogger(ValidatedTokenUseCase.class);

	@Autowired
	public ValidatedTokenUseCase(JwtUtil util, PasswordRestTokenRepository repository) {
		this.util = util;
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> validatedUrlToken(PasswordResetTokenDto token) {
		log.info("Este token esta intentando validarse" + token.getToken());
		var tokenInfo = repository.findByToken(token.getToken());

		if (!tokenInfo.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Not exist", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
		}
		var isValid = util.validateToken(token.getToken());

		if (!isValid) {
			return new ResponseEntity<>(new ResponseObject("Is invalid", TypeResponse.ERROR), HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<>(new ResponseObject("OK", TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public boolean validatedUrlToken(String token) {
		log.info("Este token esta intentando validarse" + token);
		var tokenInfo = repository.findByToken(token);

		if (!tokenInfo.isPresent()) {
			return false;
		}
		var isValid = util.validateToken(token);

		if (!isValid) {
			return false;
		}

		return true;
	}

}
