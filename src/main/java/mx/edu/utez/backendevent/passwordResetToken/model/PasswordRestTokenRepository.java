package mx.edu.utez.backendevent.passwordResetToken.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRestTokenRepository extends JpaRepository<PasswordResetToken, Long> {

}
