package mx.edu.utez.backendevent.util;

import com.resend.services.emails.model.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import java.util.List;

@Component
public class EmailQR {

	@Value("${API_KEY_RESEND}")
	private String apiKey;

	private Logger logger = LoggerFactory.getLogger(EmailSender.class);

	public void SendMail(String to, String subject, String html, String base64Content, String filename) {

		Resend resend = new Resend(apiKey);

		Attachment qrAttachment = Attachment.builder()
				.fileName(filename)
				.content(base64Content) // Base64 string content
				.build();

		CreateEmailOptions param = CreateEmailOptions.builder()
				.from("noreply@manosmexicanas.website")
				.to(to)
				.subject(subject)
				.html(html)
				.attachments(List.of(qrAttachment))
				.build();

		try {
			CreateEmailResponse data = resend.emails().send(param);
			System.out.println("Correo enviado con ID: " + data.getId());
		} catch (ResendException e) {
			System.err.println("Error al enviar el correo: " + e.getMessage());
		}
	}

}
