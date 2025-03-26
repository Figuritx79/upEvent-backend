package mx.edu.utez.backendevent.event.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.landingPage.model.LandingPage;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

	private UUID id;

	@NotBlank(groups = {Create.class, Update.class}, message = "El nombre del evento es obligatorio")
	@Size(groups = {Create.class, Update.class}, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = {Create.class, Update.class}, message = "La descripción es obligatoria")
	private String description;

	@NotNull(groups = {Create.class, Update.class}, message = "La fecha de inicio es obligatoria")
	private Date startDate;

	@NotNull(groups = {Create.class, Update.class}, message = "La fecha de fin es obligatoria")
	private Date endDate;

	private String frontPage;

	@NotNull(groups = {Create.class, Update.class}, message = "El estado es obligatorio")
	private Boolean status;

	@NotNull(groups = {Create.class, Update.class}, message = "La landing page es obligatoria")
	private LandingPage landingPage;

	public interface Create {}
	public interface Update {}
	public interface Delete {}
}
