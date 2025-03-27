package mx.edu.utez.backendevent.event.service;

import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event.model.EventDto;
import mx.edu.utez.backendevent.event.model.EventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventService {

	private final EventRepository repository;

	public EventService(EventRepository repository) {
		this.repository = repository;
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

		Event event = new Event(
				eventDto.getName(),
				eventDto.getDescription(),
				eventDto.getStartDate(),
				eventDto.getEndDate(),
				eventDto.getFrontPage());

		Event savedEvent = repository.saveAndFlush(event);
		return new ResponseEntity<>(
				new ResponseObject("Evento creado exitosamente", TypeResponse.SUCCESS),
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
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		Event event = repository.findById(id).get();
		if (!event.getWorkshops().isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No se puede eliminar el evento porque tiene talleres asociados", null,
							TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST);
		}

		repository.deleteById(id);
		return new ResponseEntity<>(
				new ResponseObject("Evento eliminado exitosamente", null, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}
}
