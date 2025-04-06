package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.role.model.Role;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class CheckerDto extends UserDto {
	private String name;
	private String lastname;
	private String email;
	private String phone;
	private Boolean status;

	private CheckerDto (String name, String lastname, String email, String phone,Boolean status) {
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.status = status;

	}





//	public CheckerDto(@NotBlank(groups = {Update.class, Delete.class,
//			VerifyCode.class}, message = "The id to update,delete,etc is mandatory") @NotNull(groups = {Update.class, Delete.class, VerifyCode.class}, message = "The id cant by null") UUID id, @NotBlank(groups = {Update.class, Insert.class}, message = "The name is mandatory") @NotNull(groups = {Update.class, Insert.class}, message = "The name cant by null") @NotEmpty(groups = {Update.class, Insert.class}, message = "The name is mandatory") String name, @NotBlank(groups = {Update.class, Insert.class}, message = "The name is mandatory") @NotNull(groups = {Update.class, Insert.class}, message = "The name cant by null") @NotEmpty(groups = {Update.class, Insert.class}, message = "The name is mandatory") String lastname, @NotBlank(groups = {Update.class, Insert.class}, message = "The name i smandatory") @NotNull(groups = {Update.class, Insert.class}, message = "The name cant by null") @NotEmpty(groups = {Update.class, Insert.class}, message = "The name is mandatory") @Email(groups = {Update.class, Insert.class}, message = "Invalid email pattern") String email, Role role, String phone) {
//		super(id, name, lastname, email, role);
//		this.phone = phone;
//	}

	public CheckerDto(String phone) {
		this.phone = phone;
	}
}
