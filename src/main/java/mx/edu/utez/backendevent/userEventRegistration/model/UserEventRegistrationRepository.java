package mx.edu.utez.backendevent.userEventRegistration.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.utez.backendevent.user.model.User;
import java.util.List;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, UserEventRegistrationId> {

	public List<UserEventRegistration> findByUser(User user);
}
