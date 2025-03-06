package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.UserDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventAdminDto extends UserDto {

	@NotNull
	@Max(value = 10, groups = { Insert.class, Update.class })
	@NotBlank(groups = { Insert.class, Update.class })
	private String phone;

	@NotNull(groups = { Insert.class, Update.class })
	@NotBlank(groups = { Insert.class, Update.class })
	private String companyName;

	private interface Insert {

	}

	private interface Update {

	}

}
