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
public class AuthRequest {

	@NotBlank(groups = { Login.class }, message = "Email is mandatory")
	@NotNull(groups = { Login.class }, message = "Email cant be null")
	@Email(groups = { Login.class }, message = "Email error format")
	@Size(max = 50)
	private String email;

	@NotBlank(groups = { Login.class }, message = "Password is mandatory")
	@NotNull(groups = { Login.class }, message = "Password cant be Null")
	@Size(max = 100)
	private String password;

	private interface Login {

	}
}
