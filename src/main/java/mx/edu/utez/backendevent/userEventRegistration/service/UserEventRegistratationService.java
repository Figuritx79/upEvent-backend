package mx.edu.utez.backendevent.userEventRegistration.service;

import java.util.UUID;

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

}
