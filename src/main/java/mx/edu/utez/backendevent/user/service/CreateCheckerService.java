package mx.edu.utez.backendevent.user.service;

import java.sql.SQLException;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.user.model.dto.CreateCheckerDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class CreateCheckerService {
	private final UserRepository repository;
	private final BCryptPasswordEncoder encoder;

	@Autowired
	public CreateCheckerService(UserRepository repository, BCryptPasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> registerChecker(@Valid CreateCheckerDto checkerDto) {
		var checkerRole = new Role();
		checkerRole.setId(4);

		var newChecker = new User();
		newChecker.setName(checkerDto.getName());
		newChecker.setLastname(checkerDto.getLastname());
		newChecker.setEmail(checkerDto.getEmail());
		newChecker.setPassword(encoder.encode(checkerDto.getPassword()));
		newChecker.setPhone(checkerDto.getPhone());
		newChecker.setRole(checkerRole);
		newChecker.setStatus(true);
		var createdChecker = repository.save(newChecker);

		if (createdChecker == null) {
			return new ResponseEntity<>(
					new ResponseObject("Error al crear el checker", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}

		return new ResponseEntity<>(
				new ResponseObject("Checker creado exitosamente", TypeResponse.SUCCESS),
				HttpStatus.CREATED
		);
	}
}
