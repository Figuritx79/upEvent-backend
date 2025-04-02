package mx.edu.utez.backendevent.userEventRegistration.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.edu.utez.backendevent.user.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, UserEventRegistrationId> {

	public List<UserEventRegistration> findByUser(User user);

	@Query(value = "SELECT * FROM user_event_registration WHERE id_event = ?1 AND id_user =?2 ;", nativeQuery = true)
	Optional<UserEventRegistration> isUserRegistered(@Param("eventId") UUID eventId, @Param("userId") UUID userId);
}
