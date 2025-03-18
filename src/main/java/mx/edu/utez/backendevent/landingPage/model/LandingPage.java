package mx.edu.utez.backendevent.landingPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;

import java.util.UUID;

@Entity
@Table(name = "landing_page")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandingPage {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne
	@JoinColumn(name = "id_event")
	private Event event;

	@Column(name = "logo", length = 255)
	private String logo;

	@Column(name = "gallery", columnDefinition = "JSON")
	private String gallery;

	@Column(name = "url", length = 200)
	private String url;

	@Column(name = "slug", length = 40)
	private String slug;
}
