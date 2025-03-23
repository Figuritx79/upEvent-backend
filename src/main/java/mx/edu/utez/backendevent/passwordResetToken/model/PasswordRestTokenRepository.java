package mx.edu.utez.backendevent.passwordResetToken.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRestTokenRepository extends JpaRepository<PasswordResetToken, Long> {
	public Optional<PasswordResetToken> findByToken(String token);
}
