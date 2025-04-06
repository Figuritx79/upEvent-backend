package mx.edu.utez.backendevent.user.service;

import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.occupation.model.Occupation;
import mx.edu.utez.backendevent.occupation.model.OccupationRepository;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.user.model.dto.CreateParticipantDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class CreateParticipantService {
	private final UserRepository userRepository;
	private final GenderRepository genderRepository;
	private final OccupationRepository occupationRepository;
	private final BCryptPasswordEncoder encoder;

	@Autowired
	public CreateParticipantService(UserRepository userRepository,
									GenderRepository genderRepository,
									OccupationRepository occupationRepository,
									BCryptPasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.genderRepository = genderRepository;
		this.occupationRepository = occupationRepository;
		this.encoder = encoder;
	}

	@Transactional(rollbackFor = {SQLException.class})
	public ResponseEntity<ResponseObject> registerParticipant(@Valid CreateParticipantDto participantDto) {
		try {
			// Verificar si el email ya existe
			if (userRepository.existsByEmail(participantDto.getEmail())) {
				return new ResponseEntity<>(
						new ResponseObject("El correo electrónico ya está registrado", TypeResponse.ERROR),
						HttpStatus.BAD_REQUEST
				);
			}

			// Obtener el rol de participante (ID 4)
			var participantRole = new Role();
			participantRole.setId(4);

			// Validar y obtener género
			Gender gender = genderRepository.findById(participantDto.getGenderId())
					.orElseThrow(() -> new IllegalArgumentException("El género especificado no existe"));

			// Validar y obtener ocupación
			Occupation occupation = occupationRepository.findById(participantDto.getOccupationId())
					.orElseThrow(() -> new IllegalArgumentException("La ocupación especificada no existe"));

			// Crear nuevo participante
			var newParticipant = new User();
			newParticipant.setName(participantDto.getName());
			newParticipant.setLastname(participantDto.getLastname());
			newParticipant.setEmail(participantDto.getEmail());
			newParticipant.setPassword(encoder.encode(participantDto.getPassword()));
			newParticipant.setBirthdDate(participantDto.getBirthDate());
			newParticipant.setResidence(participantDto.getResidence());
			newParticipant.setGender(gender);
			newParticipant.setOccupation(occupation);
			newParticipant.setRole(participantRole);
			newParticipant.setStatus(true); // Activo por defecto

			userRepository.save(newParticipant);

			return new ResponseEntity<>(
					new ResponseObject("Participante registrado exitosamente", TypeResponse.SUCCESS),
					HttpStatus.CREATED
			);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(
					new ResponseObject(e.getMessage(), TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ResponseObject("Error interno al registrar el participante", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getAllOccupationsForRegistration() {
		List<Occupation> occupations = occupationRepository.findAll();
		return new ResponseEntity<>(
				new ResponseObject("Ocupaciones disponibles", occupations, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
}
