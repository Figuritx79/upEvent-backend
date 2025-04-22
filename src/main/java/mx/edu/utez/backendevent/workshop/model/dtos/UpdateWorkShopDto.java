package mx.edu.utez.backendevent.workshop.model.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateWorkShopDto {

	@NotNull(groups = { Update.class }, message = "El ID del taller es obligatorio")
	private UUID id;

	@NotBlank(groups = { Update.class }, message = "El nombre es obligatorio")
	@Size(groups = { Update.class }, max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(groups = { Update.class }, message = "El nombre del ponente es obligatorio")
	@Size(groups = { Update.class }, max = 100, message = "El nombre del ponente no puede exceder los 100 caracteres")
	private String speakerName;

	@Min(groups = { Update.class }, value = 1, message = "La capacidad debe ser al menos 1")
	private int capacity;

	private String description;

	@NotNull(groups = { Update.class }, message = "La hora es obligatoria")
	private Time hour;

	private MultipartFile workshopImage;

	public interface Update {
	}
}
