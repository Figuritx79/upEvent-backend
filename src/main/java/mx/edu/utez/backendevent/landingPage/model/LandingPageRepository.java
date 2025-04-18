package mx.edu.utez.backendevent.landingPage.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LandingPageRepository extends JpaRepository<LandingPage, UUID> {
	boolean existsBySlug(String slug);

	boolean existsByEventId(UUID eventId);

	boolean existsBySlugAndIdNot(String slug, UUID id);

	Optional<LandingPage> findBySlug(String slug);
	@Query(value = "SELECT * FROM landing_page WHERE id_event = :uuid", nativeQuery = true)
	Optional<LandingPage> findByEvent(@Param("uuid") UUID uuid);

}
