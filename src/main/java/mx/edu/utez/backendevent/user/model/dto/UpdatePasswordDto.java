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
public class UpdatePasswordDto {
	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email debe ser válido")
	private String email;

	@NotBlank(message = "La contraseña es obligatoria")
	private String currentPassword; // Opcional: Validar contraseña actual

	@NotBlank(message = "La nueva contraseña es obligatoria")
	private String newPassword;
}
