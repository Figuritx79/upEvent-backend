package mx.edu.utez.backendevent.event.service;

import mx.edu.utez.backendevent.event.model.dtos.UpdateEventDto;
import mx.edu.utez.backendevent.util.CloudinaryUpload;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.event.model.dtos.CreateEventDto;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.userEventRegistration.model.dto.EmailDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventService {

	private final EventRepository eventrepository;
	private final CloudinaryUpload cloudinaryUpload;
	private final UserRepository userRepository;

	@Autowired
	public EventService(EventRepository eventrepository, CloudinaryUpload cloudinaryUpload,
			UserRepository userRepository) {
		this.eventrepository = eventrepository;
		this.cloudinaryUpload = cloudinaryUpload;
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findMyOwnEvents(EmailDto admin) {
		var existUser = userRepository.findByEmail(admin.getEmail());
		if (!existUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe ese admin", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		var events = eventrepository.findByAdmin(existUser.get());
		return new ResponseEntity<>(new ResponseObject("Tus eventos", events, TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<Event> events = eventrepository.findAll();
		if (events.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay eventos registrados", null, TypeResponse.WARN),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(
				new ResponseObject("Lista de eventos", events, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findById(UUID id) {
		Optional<Event> event = eventrepository.findById(id);
		if (event.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseObject("Evento encontrado", event.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findByName (String name) {
		Optional<Event> event = eventrepository.findByName(name);
		if (event.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseObject("Evento encontrado", event.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> save(CreateEventDto eventDto) {
		if (eventDto.getEndDate().before(eventDto.getStartDate())) {
			return new ResponseEntity<>(
					new ResponseObject("La fecha de fin no puede ser anterior a la fecha de inicio", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}
		var eventName = eventrepository.findByName(eventDto.getName());
		if (eventName.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("Ya existe un evento con ese nombre", null, TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		var adminEventOwner = userRepository.findByEmail(eventDto.getEmail());

		if (!adminEventOwner.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("No se encontro al admin para asignar", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		String imageUrl = "";
		if (eventDto.getFrontPage() != null && !eventDto.getFrontPage().isEmpty()) {
			try {
				imageUrl = cloudinaryUpload.UploadImage(eventDto.getFrontPage());
			} catch (IOException e) {
				return new ResponseEntity<>(
						new ResponseObject("Error al subir la imagen", null, TypeResponse.ERROR),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		var event = new Event(
				eventDto.getName(),
				eventDto.getDescription(),
				eventDto.getStartDate(),
				eventDto.getEndDate(),
				imageUrl,
				adminEventOwner.get());

		Event savedEvent = eventrepository.saveAndFlush(event);
		return new ResponseEntity<>(
				new ResponseObject("Evento creado exitosamente", savedEvent, TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

//	@Transactional(rollbackFor = { SQLException.class })
//	public ResponseEntity<ResponseObject> update(EventDto eventDto) {
//		Optional<Event> optionalEvent = eventrepository.findById(eventDto.getId());
//		if (optionalEvent.isEmpty()) {
//			return new ResponseEntity<>(
//					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
//					HttpStatus.NOT_FOUND);
//		}
//
//		if (eventDto.getEndDate().before(eventDto.getStartDate())) {
//			return new ResponseEntity<>(
//					new ResponseObject("La fecha de fin no puede ser anterior a la fecha de inicio", null,
//							TypeResponse.ERROR),
//					HttpStatus.BAD_REQUEST);
//		}
//
//		Event event = optionalEvent.get();
//		event.setName(eventDto.getName());
//		event.setDescription(eventDto.getDescription());
//		event.setStartDate(eventDto.getStartDate());
//		event.setEndDate(eventDto.getEndDate());
//
//		Event updatedEvent = eventrepository.saveAndFlush(event);
//		return new ResponseEntity<>(
//				new ResponseObject("Evento actualizado exitosamente", updatedEvent, TypeResponse.SUCCESS),
//				HttpStatus.OK);
//	}


	@Transactional
	public ResponseEntity<ResponseObject> update(UpdateEventDto eventDto) {
		Optional<Event> optionalEvent = eventrepository.findById(eventDto.getId());
		if (optionalEvent.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		Event existingEvent = optionalEvent.get();

		if (eventDto.getEndDate().before(eventDto.getStartDate())) {
			return new ResponseEntity<>(
					new ResponseObject("La fecha de fin no puede ser anterior a la fecha de inicio", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		existingEvent.setName(eventDto.getName());
		existingEvent.setDescription(eventDto.getDescription());
		existingEvent.setStartDate(eventDto.getStartDate());
		existingEvent.setEndDate(eventDto.getEndDate());

		if (eventDto.getFrontPage() != null && !eventDto.getFrontPage().isEmpty()) {
			try {
				String newImageUrl = cloudinaryUpload.UploadImage(eventDto.getFrontPage());

				existingEvent.setFrontPage(newImageUrl);

			} catch (IOException e) {
				return new ResponseEntity<>(
						new ResponseObject("Error al actualizar la imagen", null, TypeResponse.ERROR),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		Event updatedEvent = eventrepository.saveAndFlush(existingEvent);
		return new ResponseEntity<>(
				new ResponseObject("Evento actualizado correctamente", updatedEvent, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		Optional<Event> optionalEvent = eventrepository.findById(id);
		if (optionalEvent.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		Event event = optionalEvent.get();
		event.setStatus(!event.isStatus());
		eventrepository.save(event);
		return new ResponseEntity<>(
				new ResponseObject("Estado actualizado", event, TypeResponse.SUCCESS),
				HttpStatus.OK);

	}
}
