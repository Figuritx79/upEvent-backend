package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.role.model.Role;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CheckerDto extends UserDto {

	@NotNull
	@Size(max = 10, groups = { Insert.class, Update.class })
	@NotBlank(groups = { Insert.class, Update.class })
	private String phone;

	@NotNull(groups = { Insert.class, Update.class })
	private Boolean status;

	public CheckerDto(UUID id, String name, String lastname, String email, Role role, String phone, Boolean status) {
		super(id, name, lastname, email, role);
		this.phone = phone;
		this.status = status;
	}

	private interface Insert extends UserDto.Insert {}
	private interface Update extends UserDto.Update {}
}
