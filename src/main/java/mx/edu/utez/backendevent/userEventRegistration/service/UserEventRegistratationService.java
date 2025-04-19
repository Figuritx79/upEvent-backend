package mx.edu.utez.backendevent.userEventRegistration.service;

import java.sql.SQLException;
import java.util.UUID;

import mx.edu.utez.backendevent.event_checker.service.EventCheckerService;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistration;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationId;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterParticipantDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Transactional
@Service
public class UserEventRegistratationService {
	private UserRepository userRepository;
	private UserEventRegistrationRepository registrationRepository;
	private EventRepository eventRepository;

	private Logger log = LoggerFactory.getLogger(EventCheckerService.class);

	@Autowired
	public UserEventRegistratationService(UserRepository userRepository,
			UserEventRegistrationRepository registrationRepository, EventRepository eventRepository) {
		this.userRepository = userRepository;
		this.registrationRepository = registrationRepository;
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> eventRegistered(String email) {
		var existUser = userRepository.findByEmail(email);

		if (!existUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe el usuario", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var myRegisteredEvents = registrationRepository.findByUser(existUser.get());

		return new ResponseEntity<>(new ResponseObject("Eventos inscritos", myRegisteredEvents, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findParticipantByEvent(UUID idEvent) {
		var existEvent = eventRepository.findById(idEvent);

		if (!existEvent.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe ese evento", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var registerUser = registrationRepository.findByEvent(existEvent.get());

		if (registerUser.isEmpty()) {
			return new ResponseEntity<>(new ResponseObject("No hay ussuario registrados", TypeResponse.WARN),
					HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<>(new ResponseObject("Usuarios", registerUser, TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> registerParticipantToEvent(RegisterParticipantDto dto) {
		String formattedEventName = dto.getEventName().replace("-", " ");

		var existUser = userRepository.findByEmail(dto.getEmail());
		var existEvent = eventRepository.findByName(formattedEventName);

		if (!existUser.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Usuario no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		if (!existEvent.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		UserEventRegistrationId registrationId = new UserEventRegistrationId(
				existUser.get().getId(),
				existEvent.get().getId());

		if (registrationRepository.existsById(registrationId)) {
			return new ResponseEntity<>(
					new ResponseObject("El usuario ya está registrado en este evento", TypeResponse.WARN),
					HttpStatus.CONFLICT);
		}

		UserEventRegistration eventRegistration = new UserEventRegistration();
		eventRegistration.setEvent(existEvent.get());
		eventRegistration.setUser(existUser.get());
		eventRegistration.setId(registrationId);

		registrationRepository.saveAndFlush(eventRegistration);

		return new ResponseEntity<>(
				new ResponseObject("Registro exitoso", TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getUsersListByEvent(UUID idEvent) {
		var users = registrationRepository.getUsersListByEvent(idEvent);

		if (users.isEmpty()) {
			log.info("No hay usuarios inscritos al evento con ID: " + idEvent);
			return new ResponseEntity<>(
					new ResponseObject("No hay usuarios inscritos a este evento", TypeResponse.WARN),
					HttpStatus.NO_CONTENT
			);
		}

		log.info("Usuarios inscritos al evento con ID: " + idEvent);
		return new ResponseEntity<>(
				new ResponseObject("Usuarios inscritos encontrados", users, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
}
