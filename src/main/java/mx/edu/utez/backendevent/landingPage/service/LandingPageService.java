package mx.edu.utez.backendevent.landingPage.service;

import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;
import mx.edu.utez.backendevent.landingPage.model.LandingPage;
import mx.edu.utez.backendevent.landingPage.model.LandingPageDto;
import mx.edu.utez.backendevent.landingPage.model.LandingPageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LandingPageService {

	private final LandingPageRepository repository;

	public LandingPageService(LandingPageRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findAll() {
		List<LandingPage> landingPages = repository.findAll();
		if (landingPages.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("No hay landing pages registradas", null, TypeResponse.WARN),
					HttpStatus.OK
			);
		}
		return new ResponseEntity<>(
				new ResponseObject("Lista de landing pages", landingPages, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> findById(UUID id) {
		Optional<LandingPage> landingPage = repository.findById(id);
		if (landingPage.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		return new ResponseEntity<>(
				new ResponseObject("Landing page encontrada", landingPage.get(), TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional
	public ResponseEntity<ResponseObject> save(LandingPageDto landingPageDto) {
		if (repository.existsBySlug(landingPageDto.getSlug())) {
			return new ResponseEntity<>(
					new ResponseObject("El slug ya está en uso", null, TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		}

		if (repository.existsByEventId(landingPageDto.getEvent().getId())) {
			return new ResponseEntity<>(
					new ResponseObject("El evento ya tiene una landing page asociada", null, TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		}

		LandingPage landingPage = new LandingPage();
		landingPage.setEvent(landingPageDto.getEvent());
		landingPage.setLogo(landingPageDto.getLogo());
		landingPage.setGallery(landingPageDto.getGallery());
		landingPage.setUrl(landingPageDto.getUrl());
		landingPage.setSlug(landingPageDto.getSlug());

		LandingPage savedLandingPage = repository.saveAndFlush(landingPage);
		return new ResponseEntity<>(
				new ResponseObject("Landing page creada exitosamente", savedLandingPage, TypeResponse.SUCCESS),
				HttpStatus.CREATED
		);
	}

	@Transactional
	public ResponseEntity<ResponseObject> update(LandingPageDto landingPageDto) {
		Optional<LandingPage> optionalLandingPage = repository.findById(landingPageDto.getId());
		if (optionalLandingPage.isEmpty()) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}

		if (repository.existsBySlugAndIdNot(landingPageDto.getSlug(), landingPageDto.getId())) {
			return new ResponseEntity<>(
					new ResponseObject("El slug ya está en uso por otra landing page", null, TypeResponse.ERROR),
					HttpStatus.BAD_REQUEST
			);
		}

		LandingPage landingPage = optionalLandingPage.get();
		landingPage.setLogo(landingPageDto.getLogo());
		landingPage.setGallery(landingPageDto.getGallery());
		landingPage.setUrl(landingPageDto.getUrl());
		landingPage.setSlug(landingPageDto.getSlug());

		LandingPage updatedLandingPage = repository.saveAndFlush(landingPage);
		return new ResponseEntity<>(
				new ResponseObject("Landing page actualizada exitosamente", updatedLandingPage, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}

	@Transactional
	public ResponseEntity<ResponseObject> deleteById(UUID id) {
		if (!repository.existsById(id)) {
			return new ResponseEntity<>(
					new ResponseObject("Landing page no encontrada", null, TypeResponse.WARN),
					HttpStatus.NOT_FOUND
			);
		}
		repository.deleteById(id);
		return new ResponseEntity<>(
				new ResponseObject("Landing page eliminada exitosamente", null, TypeResponse.SUCCESS),
				HttpStatus.OK
		);
	}
}
