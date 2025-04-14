package mx.edu.utez.backendevent.workshop.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopDto {

	private UUID id;

	@NotBlank(groups = {Create.class, Update.class}, message = "El nombre es obligatorio")
	@Size(groups = {Create.class, Update.class}, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = {Create.class, Update.class}, message = "El nombre del ponente es obligatorio")
	@Size(groups = {Create.class, Update.class}, max = 100, message = "El nombre del ponente no puede exceder los 100 caracteres")
	private String speakerName;

	@NotNull(groups = {Create.class, Update.class}, message = "El evento es obligatorio")
	private Event event;

	@Min(groups = {Create.class, Update.class}, value = 1, message = "La capacidad debe ser al menos 1")
	private int capacity;

	private String description;

	@NotNull(groups = {Create.class, Update.class}, message = "La hora es obligatoria")
	private Time hour;

	private String image;
	private MultipartFile imageFile;

	public interface Create {}
	public interface Update {}
	public interface Delete {}
}
