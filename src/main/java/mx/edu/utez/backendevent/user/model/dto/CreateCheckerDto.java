package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckerDto extends UserDto {

	@NotNull
	@Size(max = 10, groups = { Insert.class })
	@NotBlank(groups = { Insert.class })
	private String phone;

	@NotBlank(groups = { Insert.class })
	@NotNull(groups = { Insert.class })
	private String password;

	private interface Insert extends UserDto.Insert {}
}
