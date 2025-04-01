package mx.edu.utez.backendevent.userEventRegistration.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEventRegistrationId implements Serializable {

	private UUID idUser;
	private UUID idEvent;

	public UUID getIdUser() {
		return idUser;
	}

	public void setIdUser(UUID idUser) {
		this.idUser = idUser;
	}

	public UUID getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(UUID idEvent) {
		this.idEvent = idEvent;
	}

	// es importante lo jurooooo, lo explico en cuando me pregunten
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserEventRegistrationId that = (UserEventRegistrationId) o;
		return Objects.equals(idUser, that.idUser) && Objects.equals(idEvent, that.idEvent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUser, idEvent);
	}
}
