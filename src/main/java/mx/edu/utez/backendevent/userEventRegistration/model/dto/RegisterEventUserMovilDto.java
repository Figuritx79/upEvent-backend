package mx.edu.utez.backendevent.userEventRegistration.model.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterEventUserMovilDto {
	private String email;

	private UUID idEvent;
}
