package mx.edu.utez.backendevent.user.service;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class UserService {

	@ReadOnlyProperty
	private UserRepository repository;

	public UserService(UserRepository repository) {
		this.repository = repository;
	}


	@Transactional(readOnly = true)
	public ResponseEntity<ResponseObject> getAllUser(){
		var user = repository.findAll();
		return new ResponseEntity<>(new ResponseObject("All users, doesnt matter the role ",user, TypeResponse.SUCCESS), HttpStatus.OK);
	}

}
