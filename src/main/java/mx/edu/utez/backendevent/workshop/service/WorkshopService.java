package mx.edu.utez.backendevent.workshop.service;

import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;
import mx.edu.utez.backendevent.util.CloudinaryUpload;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.workshop.model.Workshop;
import mx.edu.utez.backendevent.workshop.model.WorkshopRepository;
import mx.edu.utez.backendevent.workshop.model.dtos.CreateWorkShopDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class WorkshopService {

	private final WorkshopRepository repository;
	private final EventRepository eventRepository;
	private final CloudinaryUpload cloudinaryUpload;
	private Logger log = LoggerFactory.getLogger(WorkshopService.class);

	public WorkshopService(WorkshopRepository repository, EventRepository eventRepository,
			CloudinaryUpload cloudinaryUpload) {
		this.repository = repository;
		this.cloudinaryUpload = cloudinaryUpload;
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findByEvent(UUID idEvent) {
		var eventsExist = eventRepository.findById(idEvent);
		if (!eventsExist.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe el evento", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var avaibleWorkShops = repository.findByEvent(eventsExist.get());

		log.info(
				"Se solicito los talleres del evento " + eventsExist.get().getName() + " " + eventsExist.get().getId());
		return new ResponseEntity<>(new ResponseObject("Tallers disponibles", avaibleWorkShops, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<Workshop> workshops = repository.findAll();
		if (workshops.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay talleres registrados", null, TypeResponse.WARN),
					HttpStatus.OK);
		}
		log.info("Se solicitaron todos los taller");
		return new ResponseEntity<>(
				new ResponseObject("Lista de talleres", workshops, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findById(UUID id) {

		Optional<Workshop> workshop = repository.findById(id);
		if (workshop.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		log.info("Se solicito el evento con id" + workshop.get().getId() + " " + workshop.get().getName());
		return new ResponseEntity<>(
				new ResponseObject("Taller encontrado", workshop.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> saveWorkShop(CreateWorkShopDto createWorkShopDto) {
		var eventExist = eventRepository.findById(createWorkShopDto.getEvent());
		if (!eventExist.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var imageSpeaker = "";
		var imageWorkShop = "";
		Map<String, String> speaker = new HashMap<>();
		try {
			imageSpeaker = cloudinaryUpload.UploadImage(createWorkShopDto.getSpeakerImage());
			imageWorkShop = cloudinaryUpload.UploadImage(createWorkShopDto.getWorkshopImage());
		} catch (IOException e) {
			return new ResponseEntity<>(
					new ResponseObject("Error al subir la imagen", null, TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		speaker.put("speaker_name", createWorkShopDto.getSpeakerName());
		speaker.put("speaker_image", imageSpeaker);

		Workshop workshop = new Workshop(createWorkShopDto.getName(), speaker, eventExist.get(),
				createWorkShopDto.getCapacity(), createWorkShopDto.getDescription(), createWorkShopDto.getHour(),
				imageWorkShop, true);
		repository.saveAndFlush(workshop);
		if (workshop == null) {
			return new ResponseEntity<>(new ResponseObject("Error al crear la landing", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("Se creo el taller del eventos" + eventExist.get().getName());

		return new ResponseEntity<>(new ResponseObject("Se creo el taller", workshop, TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	// Tengo un conflico con este, ya que si el taller esta asociado a un evento, no
	// se puede eliminar
	// ademas en este caso workshop, no tiene status, asi que tenemos que ver como
	// es que hacemos ese eliminado
	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		Optional<Workshop> optionalWorkshop = repository.findById(id);

		if (optionalWorkshop.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}

		Workshop workshop = optionalWorkshop.get();
		workshop.setStatus(!workshop.isStatus());
		repository.save(workshop);

		return new ResponseEntity<>(
				new ResponseObject("Estado actualizado", workshop, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> changeStatus(UUID id) {
		Optional<Workshop> optionalWorkshop = repository.findById(id);
		if (!optionalWorkshop.isPresent()) {
			log.info("Taller no encontrado con id: {}", id);
			return new ResponseEntity<>(
					new ResponseObject("Taller no encontrado", TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		Workshop workshop = optionalWorkshop.get();
		workshop.setStatus(!workshop.isStatus());
		repository.saveAndFlush(workshop);
		log.info("Estado del usuario {} cambiado a {}", id, workshop.isStatus());
		return new ResponseEntity<>(
				new ResponseObject("Estado actualizado correctamente", TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

}
