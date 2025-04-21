package mx.edu.utez.backendevent.event.model.dtos;

import java.sql.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;
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
public class UpdateEventDto {
	@NotNull(message = "El ID del evento es obligatorio")
	private UUID id;

	@NotBlank(message = "El nombre del evento es obligatorio")
	@Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
	private String name;

	@NotBlank(message = "La descripción es obligatoria")
	private String description;

	@NotNull(message = "La fecha de inicio es obligatoria")
	private Date startDate;

	@NotNull(message = "La fecha de fin es obligatoria")
	private Date endDate;

	private MultipartFile frontPage;
}
