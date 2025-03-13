package mx.edu.utez.backendevent.user.model.dto;

import java.sql.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.occupation.model.Occupation;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParticipantDto extends UserDto {

	private Date birthdDate;

	@NotNull(groups = { Insert.class, Update.class }, message = "Cant bee null")
	private String residence;

	private Gender gender;

	private Occupation occupation;

	private interface Insert {

	}

	private interface Update {

	}

}
