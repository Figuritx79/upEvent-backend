package mx.edu.utez.backendevent.userWorkshopRegistration.service;

import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationRepository;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.UserWorkShopRegistrationRepository;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.UserWorkshopRegistration;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.UserWorkshopRegistrationId;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.CreateUserWorkshopDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.workshop.model.WorkshopRepository;

@Service
@Transactional
public class UserWorshopRegister {
	private UserEventRegistrationRepository uRegistrationRepository;
	private UserRepository userRepository;
	private UserWorkShopRegistrationRepository registrationRepository;
	private WorkshopRepository workshopRepository;

	@Autowired
	public UserWorshopRegister(UserEventRegistrationRepository uRegistrationRepository, UserRepository userRepository,
			UserWorkShopRegistrationRepository registrationRepository, WorkshopRepository workshopRepository) {
		this.uRegistrationRepository = uRegistrationRepository;
		this.userRepository = userRepository;
		this.registrationRepository = registrationRepository;
		this.workshopRepository = workshopRepository;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> saveRegistrationWorkShop(CreateUserWorkshopDto dto) {
		var existUser = userRepository.findByEmail(dto.getEmail());
		if (!existUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe el usuario", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var existeWorkshop = workshopRepository.findById(dto.getIdWorkshop());

		if (!existeWorkshop.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe el taller", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var existeRegistrationEventUser = uRegistrationRepository
				.isUserRegistered(existeWorkshop.get().getEvent().getId(), existUser.get().getId());
		if (!existeRegistrationEventUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("Tienes que estar registrado al evento", TypeResponse.WARN),
					HttpStatus.UNPROCESSABLE_ENTITY);
		}
		UserWorkshopRegistrationId embedId = new UserWorkshopRegistrationId(existUser.get().getId(),
				existeWorkshop.get().getId());

		UserWorkshopRegistration userWorkshopRegistration = new UserWorkshopRegistration(embedId, existUser.get(),
				existeWorkshop.get());

		var registration = registrationRepository.saveAndFlush(userWorkshopRegistration);

		return new ResponseEntity<>(
				new ResponseObject("Registro existoso al taller", registration, TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getWorkshopsByUserEmail(String email, UUID idEvent) {
		List<BasicWorkshopDto> workshops = registrationRepository.findWorkshopsByUserEmail(email, idEvent);

		if (workshops.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay talleres inscritos", TypeResponse.ERROR),
					HttpStatus.OK
			);
		}

		return new ResponseEntity<>(
				new ResponseObject("Talleres obtenidos", workshops, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(rollbackFor = {SQLException.class})
	public ResponseEntity<ResponseObject> validateAttendance(ValidateAttendanceDto dto) {
		Optional<User> optionalUser = userRepository.findByEmail(dto.getEmail());
		if (optionalUser.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No existe el usuario con ese email", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND
			);
		}

		User user = optionalUser.get();

		UserWorkshopRegistrationId id = new UserWorkshopRegistrationId(user.getId(), dto.getIdWorkshop());

		Optional<UserWorkshopRegistration> optionalRegistration = registrationRepository.findById(id);
		if (optionalRegistration.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay registro en este taller", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND
			);
		}

		UserWorkshopRegistration registration = optionalRegistration.get();

		if (!registration.isStatus()) {
			return new ResponseEntity<>(
					new ResponseObject("El usuario ya fue marcado como asistente", TypeResponse.WARN),
					HttpStatus.CONFLICT
			);
		}

		registration.setStatus(false);
		registrationRepository.saveAndFlush(registration);

		return new ResponseEntity<>(
				new ResponseObject("Asistencia validada correctamente", TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}


	public ResponseEntity<List<UserWorkshopRegistration>> getUsersByWorkshopId(WorkshopRequestDto dto) {
		List<UserWorkshopRegistration> users = registrationRepository.findUsersByWorkshopId(dto.getWorkshopId());
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
