package mx.edu.utez.backendevent.user.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.occupation.model.Occupation;
import mx.edu.utez.backendevent.role.model.Role;

import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantDto extends UserDto {

	@Past(groups = {Insert.class, Update.class})
	private Date birthDate;

	@NotNull(groups = {Insert.class, Update.class})
	private String residence;

	private Gender gender;

	private Occupation occupation;

	public ParticipantDto(UUID id, String name, String lastname, String email, Role role,
						  Date birthDate, String residence, Gender gender, Occupation occupation) {
		super(id, name, lastname, email, role);
		this.birthDate = birthDate;
		this.residence = residence;
		this.gender = gender;
		this.occupation = occupation;
	}

	private interface Insert extends UserDto.Insert {}
	private interface Update extends UserDto.Update {}
}
