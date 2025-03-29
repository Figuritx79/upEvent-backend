package mx.edu.utez.backendevent.event.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

	private UUID id;

	@NotBlank(groups = { Create.class, Update.class }, message = "El nombre del evento es obligatorio")
	@Size(groups = { Create.class, Update.class }, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = { Create.class, Update.class }, message = "La descripción es obligatoria")
	private String description;

	@NotNull(groups = { Create.class, Update.class }, message = "La fecha de inicio es obligatoria")
	private Date startDate;

	@NotNull(groups = { Create.class, Update.class }, message = "La fecha de fin es obligatoria")
	private Date endDate;

	private MultipartFile frontPage;

	public interface Create {
	}

	public interface Update {
	}

	public interface Delete {
	}
}
