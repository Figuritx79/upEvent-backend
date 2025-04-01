package mx.edu.utez.backendevent.userEventRegistration.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.occupation.model.OccupationRepository;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.role.model.RoleRepository;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistration;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationId;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserDto;
import mx.edu.utez.backendevent.util.EmailSender;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class RegisterEventService {
	private UserRepository userRepository;
	private EventRepository eventRepository;
	private EmailSender sender;
	private PasswordEncoder encoder;
	private GenderRepository genderRepository;
	private OccupationRepository occupationRepository;
	private UserEventRegistrationRepository registrationRepository;
	private Logger log = LoggerFactory.getLogger(RegisterEventService.class);

	public RegisterEventService(UserRepository userRepository, EventRepository eventRepository, EmailSender sender,
			PasswordEncoder encoder, GenderRepository gender, OccupationRepository occupationRepository,
			UserEventRegistrationRepository registrationRepository) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.sender = sender;
		this.encoder = encoder;
		this.genderRepository = gender;
		this.occupationRepository = occupationRepository;
		this.registrationRepository = registrationRepository;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> registerUserEvent(RegisterEventUserDto eventUserDto) {
		var eventExist = eventRepository.findById(eventUserDto.getIdEvent());
		if (!eventExist.isPresent()) {
			log.warn("No se encontro el evento");
			return new ResponseEntity<>(
					new ResponseObject("No se encontro el evento para registrarte", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND);
		}

		var exist = userRepository.findByEmail(eventUserDto.getEmail());

		if (exist.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Ya existe el usuario con ese correo", TypeResponse.WARN),
					HttpStatus.BAD_REQUEST);
		}

		var passwordEncoder = encoder.encode(eventUserDto.getPassword());
		var gender = genderRepository.findByName(eventUserDto.getGender());
		var role = new Role(3);
		var userRegister = new User(eventUserDto.getName(), eventUserDto.getLastname(),
				eventUserDto.getBirthDate(), eventUserDto.getEmail(), passwordEncoder, eventUserDto.getResidence(),
				eventUserDto.getCompanyName(), gender.get(), role);

		var user = userRepository.saveAndFlush(userRegister);

		var searchNewUser = userRepository.findByEmail(user.getEmail());

		if (!searchNewUser.isPresent()) {
			log.warn("No Se puedo creo el evento");
			return new ResponseEntity<>(
					new ResponseObject("Error creando al usario", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND);
		}
		log.info("SE CREO EL USUARIO CON" + searchNewUser.get().getId());
		var embeddedId = new UserEventRegistrationId(searchNewUser.get().getId(), eventExist.get().getId());
		var eventUserRegistration = new UserEventRegistration();
		eventUserRegistration.setId(embeddedId);
		eventUserRegistration.setEvent(eventExist.get());
		eventUserRegistration.setUser(searchNewUser.get());

		var registrationEventUser = registrationRepository.saveAndFlush(eventUserRegistration);

		if (registrationEventUser == null) {
			log.warn("No se inserto el registro al evento");
			return new ResponseEntity<>(
					new ResponseObject("Error en el registro al evento", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(
				new ResponseObject("Se hizo el registro al taller", TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

}
