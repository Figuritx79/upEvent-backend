package mx.edu.utez.backendevent.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.utez.backendevent.passwordResetToken.service.CreateTokenUseCase;
import mx.edu.utez.backendevent.security.dto.RecoveryPasswordRequest;
import mx.edu.utez.backendevent.user.model.UserRepository;
import mx.edu.utez.backendevent.util.EmailSender;
import mx.edu.utez.backendevent.util.ResponseObject;
import mx.edu.utez.backendevent.util.TypeResponse;

@Service
@Transactional
public class RecoveryPasswordUseCase {

	private Logger log = LoggerFactory.getLogger(RecoveryPasswordUseCase.class);
	private CreateTokenUseCase createTokenUseCase;
	private UserRepository repository;
	private EmailSender emailSender;
	private String html = """
			<!DOCTYPE html>
			<html lang="en">

			<head>
			  <meta charset="UTF-8">
			  <meta name="viewport" content="width=device-width, initial-scale=1.0">
			  <title>Recovery Password</title>
			</head>

			<body style="background: #0D0D0D; color: #F2F2F2; box-sizing: border-box;">
			  <div class="image" style="position: absolute; top: 0px;">
			    <img src="https://res.cloudinary.com/dt9d7lbhg/image/upload/v1742350041/Group_147_kquzlp.svg" alt="">
			  </div>
			  <main style="display: flex; justify-content: center; align-items: center; height: 100vh; width: 100vw;">
			    <section>
			      <h1 style="text-align: center;">Recuperación de Contraseña</h1>
			      <article style="display: flex; align-items: center; justify-content: center; flex-direction: column; text-align: center;">
			        <p>Hemos recibido una solicitud para restablecer tu contraseña. Si no realizaste esta solicitud, puedes ignorar este mensaje.</p>
			        <p>Para restablecer tu contraseña, haz clic en el botón de abajo.</p>
			      </article>
			      <div style="display: flex; justify-content: center; align-items: center;">
			        <a href="%s" style="color: #F2F2F2; font-weight: bold; text-decoration: none; background: #007599; padding: 8px; border-radius: 10px; transition: all .3s ease-in-out; box-shadow: 0px 10px 15px -3px rgba(38, 169, 217, .4);" onmouseover="this.style.background='#009CCC'; this.style.transform='scale(1.1)';" onmouseout="this.style.background='#007599'; this.style.transform='scale(1)';">Restablecer Contraseña</a>
			      </div>
			    </section>
			  </main>
			</body>

			</html>
						""";

	@Autowired
	public RecoveryPasswordUseCase(CreateTokenUseCase createTokenUseCase, UserRepository repository,
			EmailSender emailSender) {
		this.createTokenUseCase = createTokenUseCase;
		this.repository = repository;
		this.emailSender = emailSender;
	}

	public ResponseEntity<ResponseObject> recoveryPassword(RecoveryPasswordRequest passwordRequest) {

		log.info("Recovery Passoword " + passwordRequest.getEmail());
		var existUser = repository.findByEmail(passwordRequest.getEmail());

		if (!existUser.isPresent()) {
			return new ResponseEntity<>(new ResponseObject("No existe el usuario", TypeResponse.WARN),
					HttpStatus.NOT_FOUND);
		}

		var token = createTokenUseCase.createRecoveryToken(existUser.get().getId());
		var urlToken = "http://localhost:5173/reset-password?context=" + token;
		var htmlFormat = String.format(this.html, urlToken);

		emailSender.SendMail(existUser.get().getEmail(), "Restablecer Contraseña", htmlFormat);

		return new ResponseEntity<>(new ResponseObject("Se ha envidado el correo", TypeResponse.SUCCESS),
				HttpStatus.OK);
	}
}
