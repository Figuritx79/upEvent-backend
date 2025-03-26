package mx.edu.utez.backendevent.qr.controller;

import mx.edu.utez.backendevent.qr.service.QrEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/qr")
public class QrController {

	private final QrEmailService qrEmailService;

	public QrController(QrEmailService qrEmailService) {
		this.qrEmailService = qrEmailService;
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendQr(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String idUser = request.get("id_user");
		String idEvent = request.get("id_event");
		String idWorkshop = request.get("id_workshop");
		String name = request.get("name");

		qrEmailService.sendQrEmail(email, idUser, idEvent, idWorkshop, name);
		return ResponseEntity.ok("Correo con QR enviado correctamente.");
	}
}
