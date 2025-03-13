package mx.edu.utez.backendevent.security.dto;

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
public class ResetPasswordRequest {

	@Size(max = 255)
	@NotBlank(groups = ResetPassword.class, message = "The token is blank")
	@NotNull(groups = ResetPassword.class, message = "The token is null")
	private String token;

	@Size(max = 100)
	@NotBlank(groups = ResetPassword.class, message = "The password is blank")
	@NotNull(groups = ResetPassword.class, message = "The password is null")
	private String password;

	public interface ResetPassword {

	}
}
