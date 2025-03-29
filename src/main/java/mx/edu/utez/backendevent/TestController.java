package mx.edu.utez.backendevent;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@RestController
@RequestMapping("/api")
public class TestController {
	@GetMapping("/up")
	public ResponseEntity<ResponseObject> up() {
		return new ResponseEntity<>(new ResponseObject("Up", TypeResponse.SUCCESS), HttpStatus.OK);
	}

	@GetMapping("/level")
	public String level() {
		return "Hello";
	}
}
