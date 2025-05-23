package mx.edu.utez.backendevent.landingPage.service;

import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.landingPage.model.dtos.UpdateLandingPageDto;
import mx.edu.utez.backendevent.util.CloudinaryUpload;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.event.model.EventRepository;
import mx.edu.utez.backendevent.landingPage.model.LandingPage;
import mx.edu.utez.backendevent.landingPage.model.LandingPageDto;
import mx.edu.utez.backendevent.landingPage.model.LandingPageRepository;
import mx.edu.utez.backendevent.landingPage.model.dtos.CreateLandingPageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LandingPageService {

	private final LandingPageRepository landingRepository;

	private final EventRepository eventRepository;

	private CloudinaryUpload cloudinaryUpload;

	private Logger log = LoggerFactory.getLogger(LandingPageService.class);

	@Autowired
	public LandingPageService(LandingPageRepository landingRepository, EventRepository eventRepository,
			CloudinaryUpload cloudinaryUpload) {
		this.landingRepository = landingRepository;
		this.cloudinaryUpload = cloudinaryUpload;
		this.eventRepository = eventRepository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<LandingPage> landingPages = landingRepository.findAll();
		if (landingPages.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay landing pages registradas", null, TypeResponse.WARN),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(
				new ResponseObject("Lista de landing pages", landingPages, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findById(UUID id) {
		Optional<LandingPage> landingPage = landingRepository.findById(id);
		if (landingPage.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseObject("Landing page encontrada", landingPage.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findByEvent(UUID id) {
		Optional<LandingPage> landingPage = landingRepository.findByEvent(id);
		if (landingPage.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(
				new ResponseObject("Landing page encontrada", landingPage.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}

	@Transactional(rollbackFor = { SQLException.class })
	public ResponseEntity<ResponseObject> saveLanding(CreateLandingPageDto landing) {
		var eventData = eventRepository.findByName(landing.getEventName());

		if (!eventData.isPresent()) {
			return new ResponseEntity<>(
					new ResponseObject("No existe el evento para generar landing", TypeResponse.ERROR),
					HttpStatus.NOT_FOUND);
		}
		var gallery1Url = "";
		var gallery2Url = "";
		var galler3Url = "";
		var logo = "";
		var slug = eventData.get().getName().replace(" ", "-");
		Map<String, String> gallery = new HashMap<>();
		try {
			gallery1Url = cloudinaryUpload.UploadImage(landing.getGallery1());
			gallery2Url = cloudinaryUpload.UploadImage(landing.getGallery2());
			galler3Url = cloudinaryUpload.UploadImage(landing.getGallery3());
			logo = cloudinaryUpload.UploadImage(landing.getLogo());
		} catch (IOException e) {
			return new ResponseEntity<>(
					new ResponseObject("Error al subir la imagen", null, TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		gallery.put("gallery1", gallery1Url);
		gallery.put("gallery2", gallery2Url);
		gallery.put("gallery3", galler3Url);
		LandingPage landingPage = new LandingPage(logo, gallery, slug, eventData.get());

		LandingPage saveLandingPage = landingRepository.save(landingPage);
		if (saveLandingPage == null) {
			return new ResponseEntity<>(new ResponseObject("Error al crear la landing", TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new ResponseObject("Se creo la landing", saveLandingPage, TypeResponse.SUCCESS),
				HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseObject> findBySlug(String slug) {
		var isExistLanding = landingRepository.findBySlug(slug);

		if (!isExistLanding.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No se encontro la info", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ResponseObject("Encontrado", isExistLanding.get(), TypeResponse.SUCCESS),
				HttpStatus.OK);
	}


	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		if (!landingRepository.existsById(id)) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}
		landingRepository.deleteById(id);
		return new ResponseEntity<>(
				new ResponseObject("Landing page eliminada exitosamente", null, TypeResponse.SUCCESS),
				HttpStatus.OK);
	}
	@Transactional(rollbackFor = { SQLException.class, IOException.class })
	public ResponseEntity<ResponseObject> updateLanding(UpdateLandingPageDto landing) {
		Optional<LandingPage> existingLanding = landingRepository.findById(UUID.fromString(landing.getId()));
		if (existingLanding.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.ERROR),
					HttpStatus.NOT_FOUND);
		}

		Optional<Event> eventData = eventRepository.findByName(landing.getEventName());
		if (eventData.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Evento no encontrado", null, TypeResponse.ERROR),
					HttpStatus.NOT_FOUND);
		}

		LandingPage landingPage = existingLanding.get();

		try {
			if (landing.getLogo() != null && !landing.getLogo().isEmpty()) {
				String newLogoUrl = cloudinaryUpload.UploadImage(landing.getLogo());
				landingPage.setLogo(newLogoUrl);
			}

			Map<String, String> gallery = landingPage.getGalleryJson();
			if (gallery == null) {
				gallery = new HashMap<>();
			}

			if (landing.getGallery1() != null && !landing.getGallery1().isEmpty()) {
				String gallery1Url = cloudinaryUpload.UploadImage(landing.getGallery1());
				gallery.put("gallery1", gallery1Url);
			}

			if (landing.getGallery2() != null && !landing.getGallery2().isEmpty()) {
				String gallery2Url = cloudinaryUpload.UploadImage(landing.getGallery2());
				gallery.put("gallery2", gallery2Url);
			}

			if (landing.getGallery3() != null && !landing.getGallery3().isEmpty()) {
				String gallery3Url = cloudinaryUpload.UploadImage(landing.getGallery3());
				gallery.put("gallery3", gallery3Url);
			}

			landingPage.setGalleryJson(gallery);

			String newSlug = eventData.get().getName().replace(" ", "-");
			landingPage.setSlug(newSlug);
			landingPage.setEvent(eventData.get());

			LandingPage updatedLandingPage = landingRepository.save(landingPage);

			return new ResponseEntity<>(
					new ResponseObject("Landing page actualizada exitosamente", updatedLandingPage, TypeResponse.SUCCESS),
					HttpStatus.OK);

		} catch (IOException e) {
			log.error("Error al subir imágenes a Cloudinary: {}", e.getMessage());
			return new ResponseEntity<>(
					new ResponseObject("Error al subir las imágenes", null, TypeResponse.ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
