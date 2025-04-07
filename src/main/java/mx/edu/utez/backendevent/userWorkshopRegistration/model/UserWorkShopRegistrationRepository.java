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
import java.util.UUID;

@Repository
public interface UserWorkShopRegistrationRepository
		extends JpaRepository<UserWorkshopRegistration, UserWorkshopRegistrationId> {

		@Query("SELECT new mx.edu.utez.backendevent.userWorkshopRegistration.model.dtos.BasicWorkshopDto	(" +
				"w.id, w.name, w.description, w.hour, w.image) " +
				"FROM UserWorkshopRegistration uwr " +
				"JOIN uwr.workshop w " +
				"JOIN uwr.user u " +
				"JOIN w.event e " +
				"WHERE u.email = :email AND e.id = :idEvent")
		List<BasicWorkshopDto> findWorkshopsByUserEmail(@Param("email") String email, @Param("idEvent") UUID idEvent);

	@Query("SELECT uwr FROM UserWorkshopRegistration uwr " +
			"JOIN FETCH uwr.user u " +
			"WHERE uwr.workshop.id = :idWorkshop")
	List<UserWorkshopRegistration> findUsersByWorkshopId(@Param("idWorkshop") UUID idWorkshop);

}
