package mx.edu.utez.backendevent.event_checker.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckerAssignDto {
	@NotNull(groups = { Create.class })
	private UUID idChecker;

	@NotNull(groups = { Create.class })
	private UUID idEvent;

	// Esto es el corroe del que lo va asignar osea el admin de evento
	@NotNull(groups = { Create.class })
	@NotBlank(groups = { Create.class })
	@Email(groups = { Create.class })
	@NotEmpty(groups = { Create.class })
	private String assignedBy;

	public interface Create {

	}
}
