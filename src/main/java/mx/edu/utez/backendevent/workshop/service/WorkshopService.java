package mx.edu.utez.backendevent.workshop.service;

import mx.edu.utez.backendevent.util.CloudinaryUpload;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.workshop.model.Workshop;
import mx.edu.utez.backendevent.workshop.model.WorkshopDto;
import mx.edu.utez.backendevent.workshop.model.WorkshopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mx.edu.utez.backendevent.util.TypeResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WorkshopService {

	private final WorkshopRepository repository;
	private final CloudinaryUpload cloudinaryUpload;

	public WorkshopService(WorkshopRepository repository, CloudinaryUpload cloudinaryUpload) {
		this.repository = repository;
		this.cloudinaryUpload = cloudinaryUpload;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<Workshop> workshops = repository.findAll();
		if (workshops.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay talleres registrados", null, TypeResponse.WARN),
					HttpStatus.OK
			);
		}
		return new ResponseEntity<>(
				new ResponseObject("Lista de talleres", workshops, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findById(UUID id) {
		Optional<Workshop> workshop = repository.findById(id);
		if (workshop.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		return new ResponseEntity<>(
				new ResponseObject("Taller encontrado", workshop.get(), TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional
	public ResponseEntity<ResponseObject> save(WorkshopDto workshopDto) {
		String imageUrl = "";


		if (workshopDto.getImageFile() != null && !workshopDto.getImageFile().isEmpty()) {
			try {
				imageUrl = cloudinaryUpload.UploadImage(workshopDto.getImageFile());
			} catch (IOException e) {
				return new ResponseEntity<>(
						new ResponseObject("Error al subir la imagen", null, TypeResponse.ERROR),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		Workshop workshop = new Workshop();
		workshop.setName(workshopDto.getName());
		workshop.setSpeaker(workshopDto.getSpeaker());
		workshop.setEvent(workshopDto.getEvent());
		workshop.setCapacity(workshopDto.getCapacity());
		workshop.setDescription(workshopDto.getDescription());
		workshop.setHour(workshopDto.getHour());
		workshop.setImage(imageUrl);

		Workshop savedWorkshop = repository.saveAndFlush(workshop);
		return new ResponseEntity<>(
				new ResponseObject("Taller creado exitosamente", savedWorkshop, TypeResponse.SUCCESS),
				HttpStatus.CREATED
		);
	}

	@Transactional
	public ResponseEntity<ResponseObject> update(WorkshopDto workshopDto) {
		Optional<Workshop> optionalWorkshop = repository.findById(workshopDto.getId());
		if (optionalWorkshop.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}

		Workshop workshop = optionalWorkshop.get();
		String imageUrl = workshop.getImage(); // Mantener la imagen existente por defecto


		if (workshopDto.getImageFile() != null && !workshopDto.getImageFile().isEmpty()) {
			try {
				imageUrl = cloudinaryUpload.UploadImage(workshopDto.getImageFile());
			} catch (IOException e) {
				return new ResponseEntity<>(
						new ResponseObject("Error al subir la imagen", null, TypeResponse.ERROR),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		workshop.setName(workshopDto.getName());
		workshop.setSpeaker(workshopDto.getSpeaker());
		workshop.setEvent(workshopDto.getEvent());
		workshop.setCapacity(workshopDto.getCapacity());
		workshop.setDescription(workshopDto.getDescription());
		workshop.setHour(workshopDto.getHour());
		workshop.setImage(imageUrl);

		Workshop updatedWorkshop = repository.saveAndFlush(workshop);
		return new ResponseEntity<>(
				new ResponseObject("Taller actualizado exitosamente", updatedWorkshop, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
	//Tengo un conflico con este, ya que si el taller esta asociado a un evento, no se puede eliminar
	//ademas en este caso workshop, no tiene status, asi que tenemos que ver como es que hacemos ese eliminado
	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(
				new ResponseObject("Taller eliminado exitosamente", null, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
}
