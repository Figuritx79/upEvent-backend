package mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Time;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicWorkshopDto {
	private UUID id;
	private String name;
	private String description;
	private Time hour;
	private String image;

}
