package mx.edu.utez.backendevent.gender.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {

	public Optional<Gender> findByName(String name);

}
