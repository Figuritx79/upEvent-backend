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
public class PasswordResetTokenDto {

	@NotBlank(groups = { ValidToken.class }, message = "Token is blank")
	@NotNull(groups = { ValidToken.class }, message = "Token is null")
	@NotEmpty(groups = { ValidToken.class }, message = "Token is empty")
	private String token;

	public interface ValidToken {

	}
}
