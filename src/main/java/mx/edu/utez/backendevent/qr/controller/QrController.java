package mx.edu.utez.backendevent.qr.controller;

import mx.edu.utez.backendevent.qr.service.QrEmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/qr")
public class QrController {

	private final QrEmailService qrEmailService;

	public QrController(QrEmailService qrEmailService) {
		this.qrEmailService = qrEmailService;
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendQr(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String idEvent = request.get("idEvent");
		String event = request.get("event");
		String idWorkshop = request.get("idWorkshop");
		String workshop = request.get("workshop");

		qrEmailService.sendQrEmail(email, idEvent, event, idWorkshop, workshop);
		return ResponseEntity.ok("Correo con QR enviado correctamente.");
	}
}
