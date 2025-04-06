package mx.edu.utez.backendevent.userWorkshopRegistration.model;

import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.BasicWorkshopDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.UserWorkshopsByEmailDto;
import mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.UserWorkshopsDto;
import mx.edu.utez.backendevent.util.ResponseObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserWorkShopRegistrationRepository
		extends JpaRepository<UserWorkshopRegistration, UserWorkshopRegistrationId> {

	@Query("SELECT new mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.BasicWorkshopDto	(" +
			"w.id, w.name, w.description, w.hour, w.image) " +
			"FROM UserWorkshopRegistration uwr " +
			"JOIN uwr.workshop w " +
			"JOIN uwr.user u " +
			"WHERE u.email = :email")
	List<BasicWorkshopDto> findWorkshopsByUserEmail(@Param("email") String email);
}
