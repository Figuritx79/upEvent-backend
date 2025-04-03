package mx.edu.utez.backendevent.event_checker.service;

import java.sql.SQLException;

import org.hibernate.annotations.processing.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.event_checker.model.EventChecker;
import mx.edu.utez.backendevent.event_checker.model.EventCheckerId;
import mx.edu.utez.backendevent.event_checker.model.EventCheckerRepository;
import mx.edu.utez.backendevent.event_checker.model.dto.CheckerRegisterDto;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.user.service.CreateAdminEvent;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class EventCheckerRegister {

	private UserRepository userRepository;
	private EventRepository eventRepository;
	private EventCheckerRepository eventCheckerRepository;
	private PasswordEncoder encoder;
	private Logger log = LoggerFactory.getLogger(EventCheckerRegister.class);

	@Autowired
	public EventCheckerRegister(UserRepository userRepository, EventRepository eventRepository,
			EventCheckerRepository eventCheckerRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.eventCheckerRepository = eventCheckerRepository;
		this.encoder = encoder;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> saveChecker(CheckerRegisterDto dto) {
		Role checkerRole = new Role(4);
		var existUser = userRepository.findByEmail(dto.getEmail());
		if (existUser.isPresent()) {
			log.info("Ya existe un usuario con ese correo" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("Ya existe un usuario con ese correo", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}
		var existEvent = eventRepository.findById(dto.getIdEvent());

		if (!existEvent.isPresent()) {
			log.info("No existe ese evento" + dto.getIdEvent());
			return new ResponseEntity<>(new ResponseObject("No existe ese evento", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}

		var existAdminEvent = userRepository.findByEmail(dto.getAssignedBy());
		if (!existAdminEvent.isPresent()) {
			log.info("No existe el admin de evento" + dto.getAssignedBy());
			return new ResponseEntity<>(new ResponseObject("No existe ese admin de evento", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}

		var checker = new User(dto.getName(), dto.getLastname(), dto.getEmail(), encoder.encode(dto.getEmail()),
				dto.getPhone(), checkerRole);

		var createdChecker = userRepository.saveAndFlush(checker);

		if (createdChecker == null) {

			log.error("Error creando al checador");
			return new ResponseEntity<>(new ResponseObject("No se CREO AL CHECADOR", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		var newChecker = userRepository.findByEmail(createdChecker.getEmail());
		if (!newChecker.isPresent()) {
			log.error("Error creando al checador");
			return new ResponseEntity<>(new ResponseObject("No se CREO AL CHECADOR", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		var idEmbeed = new EventCheckerId(existEvent.get().getId(), newChecker.get().getId());

		EventChecker newEventChecker = new EventChecker();
		newEventChecker.setAssignedBy(existAdminEvent.get());
		newEventChecker.setChecker(newChecker.get());
		newEventChecker.setEvent(existEvent.get());
		newEventChecker.setId(idEmbeed);

		var assignedEvent = eventCheckerRepository.saveAndFlush(newEventChecker);

		if (assignedEvent == null) {
			log.error("No se puedo registrar el checkador a ese evenot");
			return new ResponseEntity<>(
					new ResponseObject("No se pudo registrar al checador en ese evento", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new ResponseObject("Se Registro el checador al evento", TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

}
