package mx.edu.utez.backendevent.userEventRegistration.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRegistrationRepository extends JpaRepository<UserEventRegistration, UserEventRegistrationId> {

}
