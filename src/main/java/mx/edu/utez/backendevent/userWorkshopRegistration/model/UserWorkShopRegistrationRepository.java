package mx.edu.utez.backendevent.userWorkshopRegistration.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWorkShopRegistrationRepository
		extends JpaRepository<UserWorkshopRegistration, UserWorkshopRegistrationId> {

}
