package mx.edu.utez.backendevent.event.model.dtos;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateEventDto {
	@NotBlank(groups = { Create.class, Update.class }, message = "El nombre del evento es obligatorio")
	@Size(groups = { Create.class, Update.class }, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = { Create.class, Update.class }, message = "La descripción es obligatoria")
	private String description;

	@NotNull(groups = { Create.class, Update.class }, message = "La fecha de inicio es obligatoria")
	private Date startDate;

	@NotNull(groups = { Create.class, Update.class }, message = "La fecha de fin es obligatoria")
	private Date endDate;

	@NotNull
	@Email
	@NotBlank
	private String email;

	private MultipartFile frontPage;

	public interface Create {
	}

	public interface Update {
	}

	public interface Delete {
	}
}
