package mx.edu.utez.backendevent.workshop.model.dtos;

import java.sql.Time;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
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
public class CreateWorkShopDto {
	@NotBlank(groups = { Create.class }, message = "El nombre es obligatorio")
	@Size(groups = { Create.class }, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = { Create.class }, message = "El nombre del ponente es obligatorio")
	@Size(groups = { Create.class }, max = 100, message = "El nombre del ponente no puede exceder los 100 caracteres")
	private String speakerName;

	@NotNull(groups = { Create.class }, message = "El evento es obligatorio")
	private UUID event;

	@Min(groups = { Create.class }, value = 1, message = "La capacidad debe ser al menos 1")
	private int capacity;

	private String description;

	@NotNull(groups = { Create.class }, message = "La hora es obligatoria")
	private Time hour;

	private MultipartFile speakerImage;
	private MultipartFile workshopImage;

	public interface Create {
	}
}
