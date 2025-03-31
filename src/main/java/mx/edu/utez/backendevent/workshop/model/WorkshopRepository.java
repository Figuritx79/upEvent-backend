package mx.edu.utez.backendevent.workshop.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import mx.edu.utez.backendevent.event.model.Event;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, UUID> {

	public List<Workshop> findByEvent(Event event);
}
