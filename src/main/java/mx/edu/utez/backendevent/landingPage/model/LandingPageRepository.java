package mx.edu.utez.backendevent.landingPage.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandingPageRepository extends JpaRepository<LandingPage, UUID> {
	boolean existsBySlug(String slug);

	boolean existsByEventId(UUID eventId);

	boolean existsBySlugAndIdNot(String slug, UUID id);

	Optional<LandingPage> findBySlug(String slug);
}
