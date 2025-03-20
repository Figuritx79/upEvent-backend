package mx.edu.utez.backendevent.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
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
public class RecoveryPasswordRequest {

	@Email(groups = RecoveryPassword.class, message = "The format is invalid")
	@NotNull(groups = RecoveryPassword.class, message = "Email null")
	@NotBlank(groups = RecoveryPassword.class, message = "Email blank")
	@Size(max = 50)
	private String email;

	private interface RecoveryPassword {

	}

}
