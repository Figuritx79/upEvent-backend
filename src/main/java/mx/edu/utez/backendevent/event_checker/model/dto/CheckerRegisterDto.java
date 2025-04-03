package mx.edu.utez.backendevent.event_checker.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CheckerRegisterDto {
	@NotNull(groups = { Create.class })
	@NotBlank(groups = { Create.class })
	@NotEmpty(groups = { Create.class })
	private String name;

	private String lastname;

	@NotNull(groups = { Create.class })
	@NotBlank(groups = { Create.class })
	@NotEmpty(groups = { Create.class })
	@Email(groups = { Create.class })
	private String email;

	@Size(max = 10)
	@NotNull(groups = { Create.class })
	@NotBlank(groups = { Create.class })
	@NotEmpty(groups = { Create.class })
	private String phone;

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
