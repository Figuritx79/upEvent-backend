package mx.edu.utez.backendevent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Component
public class EmailSender {

	@Value("${API_KEY_RESEND}")
	private String apiKey;

	private Logger logger = LoggerFactory.getLogger(EmailSender.class);

	public void SendMail(/* String from, */ String to, String subject, String html) {

		Resend resend = new Resend(apiKey);

		CreateEmailOptions param = CreateEmailOptions.builder()
				// .from(from)
				.to(to)
				.subject(subject)
				.html(html)
				.build();

		try {
			CreateEmailResponse data = resend.emails().send(param);
			logger.info(data.getId());
		} catch (ResendException e) {
			e.printStackTrace();
			logger.info("Error to send the email ");
		}
	}
}
