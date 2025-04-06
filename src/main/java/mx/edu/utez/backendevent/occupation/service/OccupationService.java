package mx.edu.utez.backendevent.occupation.service;

import mx.edu.utez.backendevent.occupation.model.Occupation;
import mx.edu.utez.backendevent.occupation.model.OccupationDto;
import mx.edu.utez.backendevent.occupation.model.OccupationRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OccupationService {
	private final OccupationRepository repository;

	@Autowired
	public OccupationService(OccupationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public ResponseEntity<ResponseObject> createOccupation(OccupationDto occupationDto) {
		if (repository.existsByName(occupationDto.getName())) {
			return new ResponseEntity<>(
					new ResponseObject(
							String.format("La ocupación '%s' ya está registrada", occupationDto.getName()),
							TypeResponse.ERROR
					),
					HttpStatus.BAD_REQUEST
			);
		}

		// Crear y guardar nueva ocupación
		Occupation newOccupation = new Occupation();
		newOccupation.setName(occupationDto.getName().trim());

		Occupation savedOccupation = repository.save(newOccupation);

		return new ResponseEntity<>(
				new ResponseObject(
						"Ocupación registrada exitosamente",
						savedOccupation,
						TypeResponse.SUCCESS
				),
				HttpStatus.CREATED
		);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getAllOccupations() {
		List<Occupation> occupations = repository.findAll();

		if (occupations.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject(
							"No hay ocupaciones registradas",
							TypeResponse.ERROR
					),
					HttpStatus.OK
			);
		}

		return new ResponseEntity<>(
				new ResponseObject(
						"Ocupaciones obtenidas correctamente",
						occupations,
						TypeResponse.SUCCESS
				),
				HttpStatus.OK
		);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getOccupationById(Long id) {
		Optional<Occupation> occupation = repository.findById(id);

		if (occupation.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject(
							"Ocupación no encontrada",
							TypeResponse.ERROR
					),
					HttpStatus.NOT_FOUND
			);
		}

		return new ResponseEntity<>(
				new ResponseObject(
						"Ocupación encontrada",
						occupation.get(),
						TypeResponse.SUCCESS
				),
				HttpStatus.OK
		);
	}
}
