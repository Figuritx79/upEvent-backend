package mx.edu.utez.backendevent.event_checker.service;

import java.sql.SQLException;
import java.util.Optional;

import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event_checker.model.dto.CheckerAssignDto;
import mx.edu.utez.backendevent.gender.model.Gender;
import org.hibernate.annotations.processing.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.server.ResponseStatusException;

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
	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<ResponseObject> saveChecker(CheckerRegisterDto dto) {
		// 1) Crear usuario checador (igual que antes)…
		Role checkerRole = new Role(4);
		if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(new ResponseObject("Ya existe un usuario con ese correo", TypeResponse.WARN));
		}
		User checker = new User(
				dto.getName(), dto.getLastname(),
				dto.getEmail(), encoder.encode(dto.getEmail()),
				dto.getPhone(), checkerRole
		);
		checker = userRepository.saveAndFlush(checker);

		// 2) Obtén el evento por defecto
		Event defaultEvent = eventRepository.findByName("Evento por default-1458-jais@/*5")
				.orElseThrow(() -> new RuntimeException("Evento por defecto no encontrado"));

		// 3) Valida assignedBy
		User admin = userRepository.findByEmail(dto.getAssignedBy())
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "No existe ese admin de evento"));

		// 4) Crea la relación EventChecker siempre con defaultEvent
		EventCheckerId idEmbed = new EventCheckerId(
				defaultEvent.getId(),
				checker.getId()
		);
		EventChecker evChk = new EventChecker();
		evChk.setId(idEmbed);
		evChk.setEvent(defaultEvent);
		evChk.setChecker(checker);
		evChk.setAssignedBy(admin);

		eventCheckerRepository.save(evChk);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseObject(
						"Checador creado y asignado al evento Evento por default-1458-jais@/*5",
						TypeResponse.SUCCESS
				));
	}

	@Transactional(rollbackFor = SQLException.class)
	public ResponseEntity<ResponseObject> assignChecker(CheckerAssignDto dto) {
		// Buscar el checador, el evento y el administrador usando los repositorios
		Optional<User> userOpt = userRepository.findById(dto.getIdChecker());
		Optional<Event> eventOpt = eventRepository.findById(dto.getIdEvent());
		Optional<User> userAdminOpt = userRepository.findByEmail(dto.getAssignedBy());

		if (userOpt.isPresent() && eventOpt.isPresent() && userAdminOpt.isPresent()) {
			// Recupera las instancias ya existentes
			User checker = userOpt.get();
			Event event = eventOpt.get();
			User admin = userAdminOpt.get();

			// Crear el identificador compuesto para el EventChecker
			EventCheckerId eventCheckerId = new EventCheckerId(event.getId(), checker.getId());

			EventChecker evChk = new EventChecker();
			evChk.setId(eventCheckerId);
			evChk.setEvent(event);      // Se relaciona con el evento existente
			evChk.setChecker(checker);  // Se relaciona con el usuario (chechador) existente
			evChk.setAssignedBy(admin); // Se relaciona con el usuario administrador existente

			// Guardar solamente el objeto EventChecker sin persistir de nuevo a los usuarios.
			eventCheckerRepository.saveAndFlush(evChk);
		} else {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseObject(
							"Checador no asignado",
							TypeResponse.WARN));
		}

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(new ResponseObject(
						"Checador asignado",
						TypeResponse.SUCCESS));
	}


}
