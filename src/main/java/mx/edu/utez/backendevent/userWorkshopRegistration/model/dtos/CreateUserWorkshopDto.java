package mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserWorkshopDto {
	@Email(groups = { Create.class })
	@NotEmpty(groups = { Create.class })
	@NotBlank(groups = { Create.class })
	@NotNull(groups = { Create.class })
	private String email;

	@NotNull(groups = { Create.class })
	private UUID idWorkshop;

	public interface Create {

	}
}
