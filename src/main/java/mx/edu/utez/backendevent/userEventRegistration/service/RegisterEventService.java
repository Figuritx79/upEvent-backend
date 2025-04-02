package mx.edu.utez.backendevent.userEventRegistration.service;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.occupation.model.OccupationRepository;
import mx.edu.utez.backendevent.qr.service.QrGeneratorService;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistration;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationId;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserDto;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.RegisterEventUserMovilDto;
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
	private QrGeneratorService qrGenerator;
	private Logger log = LoggerFactory.getLogger(RegisterEventService.class);
	String template = """
				<!DOCTYPE html>
				<html lang="en">

				<head>
				  <meta charset="UTF-8">
				  <meta name="viewport" content="width=device-width, initial-scale=1.0">
				  <title>Document</title>
				</head>

				<body class="body">
				  <div>

				    <div>

				    </div>
				    <div>
				      <h1 class="title">Hola {user}</h1>
				    </div>
				    <div class="text">
				    <p>¡Gracias por inscribirte en {event} </p>
				    <p>Estamos emocionados de contar con tu participación.</p>
				    <ul>
				      <li>📅 Fecha: {date}</li>
				      <li>⏰ Hora: {hour}</li>
				    </ul>
				  </div>
			<div>
			  <img src="{url}" alt="qr" style="width: 250px; height: 250px; display: block; margin: 20px auto;">
			</div>
			<div>
				<p>📱 Para inscribirte a los diferentes taller por favor usar la app movil. Esto te facilitarar la inscripcion a los diferentes tallers y los diferenetes eventos de la plataforma 😄 </p>
			</div>
				</body>
				</html>
				""";

	public RegisterEventService(UserRepository userRepository, EventRepository eventRepository, EmailSender sender,
			PasswordEncoder encoder, GenderRepository gender, OccupationRepository occupationRepository,
			UserEventRegistrationRepository registrationRepository, QrGeneratorService generatorService) {
		this.userRepository = userRepository;
		this.eventRepository = eventRepository;
		this.sender = sender;
		this.encoder = encoder;
		this.genderRepository = gender;
		this.occupationRepository = occupationRepository;
		this.registrationRepository = registrationRepository;
		this.qrGenerator = generatorService;
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
				gender.get(), role);

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
		var qrString = qrGenerator.generateQrBase64(eventExist.get().getId());
		var customHtml = template.replace("{user}", searchNewUser.get().getName())
				.replace("{event}", eventExist.get().getName())
				.replace("{date}", eventExist.get().getStartDate().toString())
				.replace("{hour}", "16 horas")
				.replace("{url}", "data:image/png;base64," + qrString);
		sender.SendMail(searchNewUser.get().getEmail(), "¡Inscripción confirmada !", customHtml);
		return new ResponseEntity<>(
				new ResponseObject("Se hizo el registro al evento", TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> registerUserEventMovil(RegisterEventUserMovilDto dto) {
		var existUser = userRepository.findByEmail(dto.getEmail());
		var existEvent = eventRepository.findById(dto.getIdEvent());
		if (!existUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Usuario no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		if (!existEvent.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Evento no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		UserEventRegistrationId registrationId = new UserEventRegistrationId(existUser.get().getId(),
				existEvent.get().getId());
		UserEventRegistration eventRegistration = new UserEventRegistration();
		eventRegistration.setEvent(existEvent.get());
		eventRegistration.setUser(existUser.get());
		eventRegistration.setId(registrationId);

		registrationRepository.saveAndFlush(eventRegistration);
		var qrString = qrGenerator.generateQrBase64(existEvent.get().getId());
		var customHtml = template.replace("{user}", existUser.get().getName())
				.replace("{event}", existEvent.get().getName())
				.replace("{date}", existEvent.get().getStartDate().toString())
				.replace("{hour}", "16 horas");
		sender.SendMail(existUser.get().getEmail(), "¡Inscripción confirmada !", customHtml);
		return new ResponseEntity<>(new ResponseObject("Registro exitos", TypeResponse.SUCCESS), HttpStatus.CREATED);

	}
}
