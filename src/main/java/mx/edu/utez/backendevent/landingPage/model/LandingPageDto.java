package mx.edu.utez.backendevent.landingPage.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandingPageDto {

	private UUID id;

	@NotNull(groups = {Create.class, Update.class}, message = "El evento es obligatorio")
	private Event event;

	@Size(max = 255, message = "La URL del logo no puede exceder los 255 caracteres")
	private String logo;

	private String gallery;

	@Size(max = 200, message = "La URL no puede exceder los 200 caracteres")
	private String url;

	@NotBlank(groups = {Create.class, Update.class}, message = "El slug es obligatorio")
	@Size(max = 40, message = "El slug no puede exceder los 40 caracteres")
	@Pattern(regexp = "^[a-z0-9-]+$", message = "El slug solo puede contener letras minúsculas, números y guiones")
	private String slug;

	public interface Create {}
	public interface Update {}
	public interface Delete {}
}
