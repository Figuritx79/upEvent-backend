package mx.edu.utez.backendevent.event.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.landingPage.model.LandingPage;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.workshop.model.Workshop;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "event")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "description", columnDefinition = "TINYTEXT")
	private String description;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "front_page", length = 255)
	private String frontPage;

	@Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
	private boolean status = true;

	@OneToOne(mappedBy = "event", cascade = CascadeType.ALL)
	@JsonIgnore
	private LandingPage landingPage;

	@ManyToOne
	@JoinColumn(name = "id_admin", nullable = true)
	private User admin;

	public Event(String name, String description, Date startDate, Date endDate,
			String frontPage, boolean status) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.frontPage = frontPage;
		this.status = status;
	}

	public Event(String name, String description, Date startDate, Date endDate, String frontPage) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.frontPage = frontPage;
	}

	public Event(String name, String description, Date startDate, Date endDate, String frontPage, User admin) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.frontPage = frontPage;
		this.admin = admin;
	}

	public Event(String name2, String description2, Date startDate2, Date endDate2, MultipartFile frontPage2) {
	}

	@OneToMany(mappedBy = "event")
	private List<Workshop> workshops;
}
