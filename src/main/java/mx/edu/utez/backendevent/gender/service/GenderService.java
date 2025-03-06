package mx.edu.utez.backendevent.gender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class GenderService {
	private GenderRepository genderRepository;

	@Autowired
	public GenderService(GenderRepository genderRepository) {
		this.genderRepository = genderRepository;
	}
	
	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getGenders(){
		return new ResponseEntity<>(new ResponseObject("Todos los genereos", genderRepository.findAll(), TypeResponse.SUCCESS), HttpStatus.OK);
	}
	
}
