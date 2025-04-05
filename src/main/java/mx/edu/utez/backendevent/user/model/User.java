package mx.edu.utez.backendevent.user.model;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.occupation.model.Occupation;
import mx.edu.utez.backendevent.passwordResetToken.model.PasswordResetToken;
import mx.edu.utez.backendevent.role.model.Role;

@Entity
@Table(name = "user", indexes = {
		@Index(name = "user_name_index", columnList = "name"),
		@Index(name = "user_email_index", columnList = "email"),
		@Index(name = "user_phone_index", columnList = "phone"),
		@Index(name = "user_status_index", columnList = "status")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
	public String name;

	@Column(name = "lastname", columnDefinition = "VARCHAR(50)", nullable = false, length = 50)
	public String lastname;

	@Column(name = "birth_date", nullable = true, columnDefinition = "DATE")
	public Date birthdDate;

	@Column(name = "email", nullable = false, unique = true, length = 50, columnDefinition = "VARCHAR(50)")
	public String email;

	@Column(name = "password", nullable = false, length = 100, columnDefinition = "VARCHAR(100)")
	private String password;

	@Column(name = "phone", nullable = true, length = 10, columnDefinition = "VARCHAR(10)")
	public String phone;

	@Column(name = "residence", nullable = true, columnDefinition = "TINYTEXT")
	public String residence;

	@Column(name = "company_name", nullable = true, columnDefinition = "VARCHAR(40)", length = 40)
	public String companyName;

	@Column(name = "status", nullable = false, columnDefinition = "BOOL DEFAULT TRUE")
	public boolean status = true;

	@ManyToOne
	@JoinColumn(name = "id_gender", columnDefinition = "BIGINT")
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "id_occupation", columnDefinition = "BIGINT")
	private Occupation occupation;

	@ManyToOne
	@JoinColumn(name = "id_role", columnDefinition = "BIGINT")
	private Role role;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<PasswordResetToken> passwordResetTokens;

	@OneToMany(mappedBy = "admin")
	@JsonIgnore
	private List<Event> events;

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

	public User(String name, String lastname, Date birthdDate, String email, String password, Gender gender,
			Role role) {
		this.name = name;
		this.lastname = lastname;
		this.birthdDate = birthdDate;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.role = role;
	}

	public User(String name, String lastname, String phone, String companyName, String email, String password,
			Role role) {
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.companyName = companyName;
		this.role = role;
	}

	public User(String name, String lastname, Date birthdDate, String email, String password, String residence,
			Gender gender, Role role) {
		this.name = name;
		this.lastname = lastname;
		this.birthdDate = birthdDate;
		this.email = email;
		this.password = password;
		this.residence = residence;
		this.gender = gender;
		this.role = role;
	}

	public User(String name, String lastname, String email, String password, String phone, Role role) {
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.role = role;
	}

}
