package mx.edu.utez.backendevent.qr.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.User;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "qr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QR {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "value", nullable = false, columnDefinition = "TINYTEXT")
	private String value;

	@ManyToOne
	@JoinColumn(name = "id_user")
	private User user;

	@Column(name = "created_at")
	private Timestamp createdAt;
}
