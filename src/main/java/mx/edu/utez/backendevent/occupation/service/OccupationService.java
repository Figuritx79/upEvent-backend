package mx.edu.utez.backendevent.occupation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.occupation.model.OccupationRepository;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class OccupationService {
    private OccupationRepository repository;

    @Autowired
    public OccupationService(OccupationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> allOccupations() {
        return new ResponseEntity<>(
                new ResponseObject("Todas las ocupaciones", repository.findAll(), TypeResponse.SUCCESS), HttpStatus.OK);
    }
}
