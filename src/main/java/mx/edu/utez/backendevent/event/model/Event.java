package mx.edu.utez.backendevent.event.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.landingPage.model.LandingPage;
import mx.edu.utez.backendevent.workshop.model.Workshop;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

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

	@Column(name = "status")
	private boolean status;

	@ManyToOne
	@JoinColumn(name = "id_landing_page")
	private LandingPage landingPage;

	@OneToMany(mappedBy = "event")
	private List<Workshop> workshops;
}
