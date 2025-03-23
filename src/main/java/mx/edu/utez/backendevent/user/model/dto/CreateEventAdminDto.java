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
public class CreateEventAdminDto {

	@NotBlank(groups = { Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Insert.class }, message = "The name is mandatory")
	private String name;

	@NotBlank(groups = { Insert.class }, message = "The name is mandatory")
	@NotNull(groups = { Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Insert.class }, message = "The name is mandatory")
	private String lastname;

	@NotNull
	@Size(max = 10, groups = { Insert.class, })
	@NotBlank(groups = { Insert.class, })
	private String phone;

	@NotNull(groups = { Insert.class, })
	@NotBlank(groups = { Insert.class, })
	private String companyName;

	@NotBlank(groups = { Insert.class }, message = "The name i smandatory")
	@NotNull(groups = { Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Insert.class }, message = "The name is mandatory")
	@Email(groups = { Insert.class }, message = "Invalid email pattern")
	private String email;

	@NotBlank(groups = { Insert.class }, message = "The name i smandatory")
	@NotNull(groups = { Insert.class }, message = "The name cant by null")
	@NotEmpty(groups = { Insert.class }, message = "The name is mandatory")
	private String password;

	private interface Insert {

	}

}
