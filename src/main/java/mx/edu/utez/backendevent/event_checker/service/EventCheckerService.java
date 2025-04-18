package mx.edu.utez.backendevent.event_checker.service;

import mx.edu.utez.backendevent.landingPage.model.LandingPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.event_checker.model.EventCheckerRepository;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventCheckerService {
	private EventCheckerRepository eventCheckerRepository;

	private UserRepository userRepository;

	private EventRepository eventRepository;

	private Logger log = LoggerFactory.getLogger(EventCheckerService.class);

	@Autowired
	public EventCheckerService(EventCheckerRepository eventCheckerRepository, UserRepository userRepository,
			EventRepository eventRepository) {
		this.eventCheckerRepository = eventCheckerRepository;
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> assignedEvents(EmailDto dto) {
		var existeChecker = userRepository.findByEmail(dto.getEmail());
		if (!existeChecker.isPresent()) {
			log.info("Se trato de ver sus evento asignados" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("No se encontro al checador", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var assignedEvents = eventCheckerRepository.findByChecker(existeChecker.get());

		if (assignedEvents.isEmpty()) {
			log.info("No tiene eventos asignados" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("No tienes eventos asiganados", TypeResponse.WARN),
					HttpStatus.NO_CONTENT);
		}
		log.info("Eventos del checador" + dto.getEmail());

		return new ResponseEntity<>(new ResponseObject("Eventos Asignados", assignedEvents, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> ownChekers(EmailDto dto) {
		var existeChecker = userRepository.findByEmail(dto.getEmail());
		if (!existeChecker.isPresent()) {
			log.info("Trato de visualizar a sus checadores" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("No tienes cuenta", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var myCheckers = eventCheckerRepository.findDistinctCheckerByAssignedBy(existeChecker.get().getId());

		if (myCheckers.isEmpty()) {
			log.info("No tiene checadores" + dto.getEmail());
			return new ResponseEntity<>(new ResponseObject("No tienes checadores disponibles", TypeResponse.WARN),
					HttpStatus.NO_CONTENT);
		}
		log.info("Eventos del checador" + dto.getEmail());

		return new ResponseEntity<>(new ResponseObject("Checadores disponibles", myCheckers, TypeResponse.SUCCESS),
				HttpStatus.OK);

	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> checkersByEvent(UUID idEvent) {
		var checkers = eventCheckerRepository.findCheckersByEventId(idEvent);

		if (checkers.isEmpty()) {
			log.info("No hay checadores asignados al evento con ID: " + idEvent);
			return new ResponseEntity<>(
					new ResponseObject("No hay checadores asignados a este evento", TypeResponse.WARN),
					HttpStatus.NO_CONTENT
			);
		}

		log.info("Checadores asignados al evento con ID: " + idEvent);
		return new ResponseEntity<>(
				new ResponseObject("Checadores del evento encontrados", checkers, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
}
