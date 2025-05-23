package mx.edu.utez.backendevent.userEventRegistration.model.dto;

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
public class RegisterEventUserMovilDto {
	@Email
	@NotBlank
	@NotEmpty
	@NotNull
	private String email;

	@NotNull
	private UUID idEvent;
}
