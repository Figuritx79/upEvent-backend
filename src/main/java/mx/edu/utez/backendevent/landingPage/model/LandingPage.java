package mx.edu.utez.backendevent.landingPage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "landing_page", indexes = {
		@Index(name = "landig_logo", columnList = "logo"),
		@Index(name = "landing_slug", columnList = "slug")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LandingPage {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "logo", length = 255)
	private String logo;

	// Nos permite que jdbc mapee un Map a json y es mas sencillo de usar
	// https://www.baeldung.com/hibernate-persist-json-object
	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "gallery", columnDefinition = "JSON")
	private Map<String, String> galleryJson = new HashMap<>();

	@Column(name = "url", length = 200)
	private String url;

	@Column(name = "slug", length = 40, unique = true)
	private String slug;

	@OneToOne
	@JoinColumn(name = "id_event", referencedColumnName = "id", nullable = false)
	private Event event;

	public LandingPage(String logo, Map<String, String> galleryJson, String slug, Event event) {
		this.logo = logo;
		this.galleryJson = galleryJson;
		this.slug = slug;
		this.event = event;
	}

}
