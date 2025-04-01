package mx.edu.utez.backendevent.userEventRegistration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Transactional
@Service
public class UserEventRegistratationService {
	private UserRepository userRepository;
	private UserEventRegistrationRepository registrationRepository;

	@Autowired
	public UserEventRegistratationService(UserRepository userRepository,
			UserEventRegistrationRepository registrationRepository) {
		this.userRepository = userRepository;
		this.registrationRepository = registrationRepository;
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

}
