package mx.edu.utez.backendevent.passwordResetToken.service;

import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.passwordResetToken.model.PasswordResetToken;
import mx.edu.utez.backendevent.passwordResetToken.model.PasswordRestTokenRepository;
import mx.edu.utez.backendevent.security.util.JwtUtil;
import mx.edu.utez.backendevent.user.model.User;

@Transactional
@Service
public class CreateTokenUseCase {

	private PasswordRestTokenRepository repository;
	private Logger log = LoggerFactory.getLogger(CreateTokenUseCase.class);
	private JwtUtil jwt;

	public CreateTokenUseCase(PasswordRestTokenRepository repository, JwtUtil jwtUtil) {
		this.jwt = jwtUtil;
		this.repository = repository;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public String createRecoveryToken(UUID id) {
		var token = jwt.generateToken(id);

		User user = new User();
		user.setId(id);
		PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);

		repository.saveAndFlush(passwordResetToken);
		log.info("Recovery token is created");
		return token;
	}

}
