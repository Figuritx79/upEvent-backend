package mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ValidateAttendanceDto {
	private String email;
	private UUID idWorkshop;
}
