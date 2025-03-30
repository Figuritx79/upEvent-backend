package mx.edu.utez.backendevent.event.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.utez.backendevent.event.model.dtos.MyOwnEventDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
	// Lo hacemos por medio del name o por medio del id?
	Optional<Event> findByName(String name);

	List<Event> findAllByStatusTrue();

	@Query(value = "SELECT e.name, e.description, e.start_date, e.end_date ,e.front_page FROM event e  INNER JOIN user u  ON  e.id_admin = u.id WHERE u.id = ?1", nativeQuery = true)
	List<MyOwnEventDto> findByAdminId(@Param("id") UUID id);
}
