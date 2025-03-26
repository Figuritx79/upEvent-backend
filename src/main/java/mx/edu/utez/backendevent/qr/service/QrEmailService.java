package mx.edu.utez.backendevent.qr.service;

import mx.edu.utez.backendevent.util.EmailQR;
import org.springframework.stereotype.Service;

@Service
public class QrEmailService {

	private final EmailQR emailQR;
	private final QrGeneratorService qrGeneratorService;

	public QrEmailService(EmailQR emailQR, QrGeneratorService qrGeneratorService) {
		this.emailQR = emailQR;
		this.qrGeneratorService = qrGeneratorService;
	}

	public void sendQrEmail(String email, String idUser, String idEvent, String idWorkshop, String name) {

		// Datos para generar el QR
		String qrData = String.format("{\"id_user\": \"%s\", \"id_event\": \"%s\", \"id_workshop\": \"%s\", \"name\": \"%s\"}",
				idUser, idEvent, idWorkshop, name);

		// Generar QR en Base64
		String qrBase64 = qrGeneratorService.generateQrBase64(qrData);

		// Contenido del correo
		String htmlContent = """
                <html>
                <body>
                    <h2>¡Registro exitoso en el evento!</h2>
                    <p>Gracias por registrarte. Tu código QR está adjunto en este correo.</p>
                </body>
                </html>
                """;

		// Enviar el correo con el QR adjunto
		emailQR.SendMail(email, "Tu QR de acceso al evento", htmlContent, qrBase64, "codigo_qr.png");
	}
}
