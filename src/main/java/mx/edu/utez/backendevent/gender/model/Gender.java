package mx.edu.utez.backendevent.gender.model;

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

@Entity
@Table(name = "gender")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gender {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", unique = true, columnDefinition = "VARCHAR(40)")
	private String name;

	@OneToMany(mappedBy = "gender")
	@JsonIgnore
	private List<User> user;

	public Gender(String name) {
		this.name = name;
	}

}
