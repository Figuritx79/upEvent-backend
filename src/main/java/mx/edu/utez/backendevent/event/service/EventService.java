package mx.edu.utez.backendevent.event.service;

import mx.edu.utez.backendevent.util.CloudinaryUpload;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.model.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventService {

	private final EventRepository repository;
	private final CloudinaryUpload cloudinaryUpload;

	public EventService(EventRepository repository, CloudinaryUpload cloudinaryUpload) {
		this.repository = repository;
		this.cloudinaryUpload = cloudinaryUpload;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<Event> events = repository.findAll();
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
		Optional<Event> event = repository.findById(id);
		if (event.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseObject("Evento encontrado", event.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<ResponseObject> save(EventDto eventDto) {
		if (eventDto.getEndDate().before(eventDto.getStartDate())) {
			return new ResponseEntity<>(
					new ResponseObject("La fecha de fin no puede ser anterior a la fecha de inicio", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		if (repository.existsByName(eventDto.getName())) {
			return new ResponseEntity<>(
					new ResponseObject("Ya existe un evento con ese nombre", null, TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
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

		Event event = new Event(
				eventDto.getName(),
				eventDto.getDescription(),
				eventDto.getStartDate(),
				eventDto.getEndDate(),
				imageUrl);

		Event savedEvent = repository.saveAndFlush(event);
		return new ResponseEntity<>(
				new ResponseObject("Evento creado exitosamente", savedEvent, TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	@Transactional
	public ResponseEntity<ResponseObject> update(EventDto eventDto) {
		Optional<Event> optionalEvent = repository.findById(eventDto.getId());
		if (optionalEvent.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		if (eventDto.getEndDate().before(eventDto.getStartDate())) {
			return new ResponseEntity<>(
					new ResponseObject("La fecha de fin no puede ser anterior a la fecha de inicio", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		Event event = optionalEvent.get();
		event.setName(eventDto.getName());
		event.setDescription(eventDto.getDescription());
		event.setStartDate(eventDto.getStartDate());
		event.setEndDate(eventDto.getEndDate());

		Event updatedEvent = repository.saveAndFlush(event);
		return new ResponseEntity<>(
				new ResponseObject("Evento actualizado exitosamente", updatedEvent, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		Optional<Event> optionalEvent = repository.findById(id);
		if (optionalEvent.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		Event event = optionalEvent.get();

		if (!event.getWorkshops().isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No se puede desactivar el evento porque tiene talleres asociados", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		event.setStatus(false);
		repository.save(event);

		return new ResponseEntity<>(
				new ResponseObject("Evento desactivado (eliminado lógico)", event, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}
}
