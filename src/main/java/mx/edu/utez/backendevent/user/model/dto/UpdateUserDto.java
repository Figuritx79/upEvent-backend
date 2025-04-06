package mx.edu.utez.backendevent.user.model.dto;

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
public class UpdateUserDto {
	@NotBlank(message = "El email actual es obligatorio")
	@Email(message = "El email debe ser válido")
	private String currentEmail;

	@Email(message = "El nuevo email debe ser válido")
	private String newEmail;

	private String companyName;

	private String name;
	private String lastname;
	private String phone;
}
