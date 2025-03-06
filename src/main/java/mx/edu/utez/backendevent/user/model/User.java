package mx.edu.utez.backendevent.user.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.occupation.model.Occupation;
import mx.edu.utez.backendevent.role.model.Role;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name" , columnDefinition = "VARCHAR(50)" , nullable = false, length = 50)
	private String name;

	@Column(name = "lastname" , columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
	private String lastname;

	@Column(name = "birth_date" , nullable = true , columnDefinition = "DATE")
	private Date birthdDate;

	@Column(name = "email", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
	private String email;

	@Column(name = "password", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
	private String password;

	@Column(name = "phone", nullable = true, length = 10, columnDefinition ="VARCHAR(10)")
	private String phone;

	@Column(name = "residence", nullable = true , columnDefinition = "TINYTEXT")
	private String residence;

	@Column(name = "company_name", nullable = true, columnDefinition = "VARCHAR(40)", length = 40)
	private String companyName;


	@ManyToOne
	@JoinColumn(name = "id_gender" , columnDefinition = "BIGINT")
	private Gender gender; 

	@ManyToOne
	@JoinColumn(name = "id_occupation", columnDefinition = "BIGINT")
	private Occupation occupation; 

	@ManyToOne
	@JoinColumn(name = "id_role", columnDefinition = "BIGINT")
	private Role role;

	public User(String name, String lastname, Date birthdDate, String email, String password, String phone,
			String residence, String companyName, Gender gender, Occupation occupation, Role role) {
		this.name = name;
		this.lastname = lastname;
		this.birthdDate = birthdDate;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.residence = residence;
		this.companyName = companyName;
		this.gender = gender;
		this.occupation = occupation;
		this.role = role;
	}


}
