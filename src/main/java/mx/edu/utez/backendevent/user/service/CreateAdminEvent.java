package mx.edu.utez.backendevent.user.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.user.model.dto.CreateEventAdminDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class CreateAdminEvent {
	private UserRepository repository;

	private BCryptPasswordEncoder encoder;

	@Autowired
	public CreateAdminEvent(UserRepository repository, BCryptPasswordEncoder encoder) {
		this.repository = repository;
		this.encoder = encoder;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> registerAdmin(CreateEventAdminDto adminDto) {
		var adminEventRole = new Role();
		adminEventRole.setId(2);
		var newAdminEvent = new User();
		newAdminEvent.setName(adminDto.getName());
		newAdminEvent.setLastname(adminDto.getLastname());
		newAdminEvent.setEmail(adminDto.getEmail());
		newAdminEvent.setPassword(encoder.encode(adminDto.getPassword()));
		newAdminEvent.setCompanyName(adminDto.getCompanyName());
		newAdminEvent.setPhone(adminDto.getPhone());
		newAdminEvent.setRole(adminEventRole);

		var createAdminEvent = repository.save(newAdminEvent);

		if (createAdminEvent == null) {
			return new ResponseEntity<>(new ResponseObject(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new ResponseObject("CREADO", TypeResponse.SUCCESS), HttpStatus.CREATED);
	}

}
