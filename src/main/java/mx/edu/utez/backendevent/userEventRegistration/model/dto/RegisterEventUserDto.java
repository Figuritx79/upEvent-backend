package mx.edu.utez.backendevent.userEventRegistration.model.dto;

import java.sql.Date;
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
public class RegisterEventUserDto {
	@NotNull(groups = { Register.class })
	private UUID idEvent;
	private String name;
	@NotEmpty(groups = { Register.class })
	@NotNull(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String lastname;
	@Email(groups = { Register.class })
	@NotNull(groups = { Register.class })
	@NotEmpty(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String email;
	@NotNull(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String gender;
	@NotNull(groups = { Register.class })
	private Date birthDate;
	@NotNull(groups = { Register.class })
	@NotEmpty(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String password;
	@NotNull(groups = { Register.class })
	@NotEmpty(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String occupation;
	@NotNull(groups = { Register.class })
	@NotEmpty(groups = { Register.class })
	@NotBlank(groups = { Register.class })
	private String residence;

	public interface Register {

	}
}
