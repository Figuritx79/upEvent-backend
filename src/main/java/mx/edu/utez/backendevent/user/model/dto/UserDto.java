package mx.edu.utez.backendevent.user.model.dto;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.role.model.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	@NotBlank(groups = { Update.class, Delete.class,
			VerifyCode.class }, message = "The id to update,delete,etc is mandatory")
	@NotNull(groups = { Update.class, Delete.class, VerifyCode.class }, message = "The id cant by null")
	public UUID id;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	public String name;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	public String lastname;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name i smandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@Email(groups = { Update.class, Insert.class }, message = "Invalid email pattern")
	public String email;

	private Role role;

	protected interface Insert {

	}

	protected interface Update {

	}

	protected interface Read {

	}

	protected interface Delete {

	}

	protected interface VerifyCode {

	}
}
