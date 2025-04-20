package mx.edu.utez.backendevent.event_checker.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.edu.utez.backendevent.user.model.User;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventCheckerRepository extends JpaRepository<EventChecker, EventCheckerId> {

	List<EventChecker> findByChecker(User checker);

	@Query(value = "SELECT e.id_checker, MIN(e.id_event) as id_event, MIN(e.assigned_by) as assigned_by " +
			"FROM event_checker e " +
			"WHERE e.assigned_by = :assignedBy " +
			"GROUP BY e.id_checker",
			nativeQuery = true)
	List<EventChecker> findDistinctCheckerByAssignedBy(@Param("assignedBy") UUID assignedBy);

	@Query(value = "SELECT e.id_checker, e.id_event, e.assigned_by " +
			"FROM event_checker e " +
			"WHERE e.id_event = :eventId",
			nativeQuery = true)
	List<EventChecker> findCheckersByEventId(@Param("eventId") UUID eventId);

	List<EventChecker> findByEventIdAndAssignedById(UUID eventId, UUID assignedById);

}
