package mx.edu.utez.backendevent.userWorkshopRegistration.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.UserWorkshopRegistrationId;
import mx.edu.utez.backendevent.workshop.model.Workshop;
import org.hibernate.annotations.Comment;

import java.sql.Timestamp;

@Entity
@Table(name = "user_workshop_registration")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserWorkshopRegistration {

	@EmbeddedId
	private UserWorkshopRegistrationId id; // Clave primaria compuesta

	@ManyToOne
	@MapsId("idUser") // Mapea idUser de la clave compuesta
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@ManyToOne
	@MapsId("idEvent") // Mapea idEvent de la clave compuesta
	@JoinColumn(name = "id_event", nullable = false)
	private Workshop workshop;

	@Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
	private boolean status = true;

	@Column(name = "created_at", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	public UserWorkshopRegistration(UserWorkshopRegistrationId id, User user, Workshop workshop) {
		this.id = id;
		this.user = user;
		this.workshop = workshop;
	}

}
