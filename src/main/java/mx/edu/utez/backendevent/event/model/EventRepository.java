package mx.edu.utez.backendevent.event.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
	//Lo hacemos por medio del name o por medio del id?
	boolean existsByName(String name);

}
