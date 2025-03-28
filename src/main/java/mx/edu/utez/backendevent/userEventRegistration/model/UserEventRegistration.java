package mx.edu.utez.backendevent.userEventRegistration.model; //no corrijan esto

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.userEventRegistration.model.UserEventRegistrationId;

import java.sql.Timestamp;

@Entity
@Table(name = "user_event_registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventRegistration {

	@EmbeddedId
	private UserEventRegistrationId id;

	@ManyToOne
	@MapsId("idUser")
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@ManyToOne
	@MapsId("idEvent")
	@JoinColumn(name = "id_event", nullable = false)
	private Event event;

	@Column(name = "status")
	private boolean status;

	@Column(name = "created_at", nullable = false)
	private Timestamp createdAt;
}
