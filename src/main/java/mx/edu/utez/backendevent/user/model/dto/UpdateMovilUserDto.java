package mx.edu.utez.backendevent.user.model.dto;

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
public class UpdateMovilUserDto {
	@Size(max = 10, groups = { Update.class })
	private String phone;

	private String password;

	@NotBlank
	@NotEmpty
	@NotNull
	@Email
	private String email;

	public interface Update {

	}
}
