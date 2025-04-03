package mx.edu.utez.backendevent.event_checker.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.utez.backendevent.user.model.User;
import java.util.List;

@Repository
public interface EventCheckerRepository extends JpaRepository<EventChecker, EventCheckerId> {

	List<EventChecker> findByChecker(User checker);

	List<EventChecker> findByAssignedBy(User assignedBy);
}
