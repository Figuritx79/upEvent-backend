package mx.edu.utez.backendevent.passwordResetToken.model;

import java.security.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.User;

@Entity
@Table(name = "password_reset_token", indexes = {
		@Index(name = "password_reset_token_token_index", columnList = "token")
})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "token", nullable = false, columnDefinition = "VARCHAR(255)")
	private String token;

	@Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@ManyToOne()
	@JoinColumn(name = "id_user")
	private User user;

	public PasswordResetToken(String token, User user) {
		this.token = token;
		this.user = user;
	}

}
