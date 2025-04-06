package mx.edu.utez.backendevent.user.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mx.edu.utez.backendevent.user.model.dto.CheckerDto;
import mx.edu.utez.backendevent.user.model.dto.EventAdminDto;
import mx.edu.utez.backendevent.user.model.dto.ParticipantDto;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	public Optional<User> findByEmail(String email);

	@Query(value = "SELECT * FROM user WHERE id_role = 1", nativeQuery = true)
	public List<User> findSuperAdmin();

	@Query(value = "SELECT * FROM user u WHERE id_role = 2", nativeQuery = true)
	public List<EventAdminDto> findAdminEvent();

	@Query(value = "SELECT * FROM user WHERE id_role = 3", nativeQuery = true)
	public List<ParticipantDto> findNormal();

	@Query(value = "SELECT u.name, u.lastname, u.email, u.phone, u.status FROM user u WHERE id_role = 4", nativeQuery = true)
	public List<CheckerDto> findCheckers();

	public Optional<User> findByName(String name);

	boolean existsByEmail(String email);
}
