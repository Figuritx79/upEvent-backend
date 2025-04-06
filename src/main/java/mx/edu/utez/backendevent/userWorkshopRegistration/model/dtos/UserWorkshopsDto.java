package mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Time;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkshopsDto {
	private UUID workshopId;
	private String name;
	private Map<String, String> speaker;
	private String description;
	private Time hour;
	private String image;
}
