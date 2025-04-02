package mx.edu.utez.backendevent.event.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.utez.backendevent.event.model.dtos.MyOwnEventDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import mx.edu.utez.backendevent.user.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
	// Lo hacemos por medio del name o por medio del id?
	Optional<Event> findByName(String name);

	List<Event> findAllByStatusTrue();

	// @Query(value = "SELECT * FROM event e INNER JOIN user u ON e.id_admin = u.id
	// WHERE u.id = ?1", nativeQuery = true)
	// List<Event> findByAdminId(@Param("id") UUID id);

	List<Event> findByAdmin(User admin);
}
