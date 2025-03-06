package mx.edu.utez.backendevent.occupation.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.user.model.User;

@Table(name = "occupation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", columnDefinition = "VARCHAR(40)", nullable = false)
    private String name;

	@OneToMany(mappedBy = "occupation")
	@JsonIgnore
	private List<User> users;

}
