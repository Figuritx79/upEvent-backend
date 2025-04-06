package mx.edu.utez.backendevent.occupation.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupationDto {
	private Long id;

	@NotNull(groups = {Insert.class, Update.class}, message = "El nombre es obligatorio")
	@NotBlank(groups = {Insert.class, Update.class}, message = "El nombre no puede estar vacío")
	@Size(max = 40, groups = {Insert.class, Update.class}, message = "El nombre no puede exceder los 40 caracteres")
	private String name;

	public interface Insert {}
	public interface Update {}
	public interface Delete {}
	public interface Read {}
}
