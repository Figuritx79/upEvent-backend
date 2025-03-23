package mx.edu.utez.backendevent.passwordResetToken.service;

import org.slf4j.LoggerFactory;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.passwordResetToken.model.PasswordRestTokenRepository;
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
	public ResponseEntity<ResponseObject> validatedUrlToken(String token) {

		var isValid = util.validateToken(token);

		if (!isValid) {
			return new ResponseEntity<>(new ResponseObject("Is invalid", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
		}
		var tokenInfo = repository.findByToken(token);

		if (!tokenInfo.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Not exist", TypeResponse.ERROR), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ResponseObject("OK", TypeResponse.SUCCESS), HttpStatus.OK);
	}

}
