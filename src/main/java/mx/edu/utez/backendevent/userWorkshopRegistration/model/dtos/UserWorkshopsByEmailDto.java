package mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkshopsByEmailDto {
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email debe tener un formato válido")
	private String email;
}
