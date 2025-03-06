package mx.edu.utez.backendevent.user.model;

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
	private UUID id;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	private String name;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	private String lastname;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name i smandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name is mandatory")
	@Email(groups = { Update.class, Insert.class }, message = "Invalid email pattern")
	private String email;

	@NotBlank(groups = { Update.class, Insert.class }, message = "The name ismandatory")
	@NotNull(groups = { Update.class, Insert.class }, message = "The name cant bynull")
	@NotEmpty(groups = { Update.class, Insert.class }, message = "The name ismandatory")
	private String password;

	private Role role;

	private interface Insert {

	}

	private interface Update {

	}

	private interface Read {

	}

	private interface Delete {

	}

	private interface VerifyCode {

	}
}
