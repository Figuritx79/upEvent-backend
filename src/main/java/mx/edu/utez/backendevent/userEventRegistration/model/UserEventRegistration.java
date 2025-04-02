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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_event_registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEventRegistration {

	@EmbeddedId
	@JsonIgnore
	private UserEventRegistrationId id;

	@ManyToOne
	@MapsId("idUser")
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@ManyToOne
	@MapsId("idEvent")
	@JoinColumn(name = "id_event", nullable = false)
	private Event event;

	@Column(name = "status", columnDefinition = "TINYINT DEFAULT 1")
	private boolean status = true;

	@Column(name = "created_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;
}
