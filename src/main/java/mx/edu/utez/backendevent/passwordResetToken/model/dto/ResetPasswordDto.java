package mx.edu.utez.backendevent.passwordResetToken.model.dto;

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
public class ResetPasswordDto {

	@NotBlank(groups = { ResetPassword.class }, message = "The token is Blank")
	@NotEmpty(groups = { ResetPassword.class }, message = "The token is  Empty")
	@NotNull(groups = { ResetPassword.class }, message = "The token is Null")
	private String token;

	@NotBlank(groups = { ResetPassword.class }, message = "The password is Blank")
	@NotEmpty(groups = { ResetPassword.class }, message = "The password Is Empty")
	@NotNull(groups = { ResetPassword.class }, message = "The password Is Null")
	private String password;

	public interface ResetPassword {

	}

}
