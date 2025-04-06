package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateParticipantDto extends UserDto {

	@NotNull(groups = {Insert.class})
	@Past(groups = {Insert.class}, message = "La fecha de nacimiento debe ser en el pasado")
	private Date birthDate;

	@NotNull(groups = {Insert.class}, message = "La residencia es obligatoria")
	private String residence;

	@NotNull(groups = {Insert.class}, message = "El género es obligatorio")
	private Long genderId;

	@NotNull(groups = {Insert.class}, message = "La ocupación es obligatoria")
	private Long occupationId;

	@NotBlank(groups = {Insert.class})
	@NotNull(groups = {Insert.class})
	private String password;

	private interface Insert extends UserDto.Insert {}
}
