package mx.edu.utez.backendevent.workshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;
//import mx.edu.utez.backendevent.speaker.model.Speaker;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "workshop", indexes = {
		@Index(name = "workshop_name", columnList = "name"),
		@Index(name = "workshop_image", columnList = "workshop_image")
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Workshop {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(name = "speaker", columnDefinition = "JSON")
	private Map<String, String> speakerInfo = new HashMap<>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_event", nullable = false)
	private Event event;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "description", columnDefinition = "TINYTEXT")
	private String description;

	@Column(name = "hour")
	private Time hour;

	@Column(name = "workshop_image", length = 255)
	private String image;

	@Column(name = "status", columnDefinition = "BOOL DEFAULT TRUE")
	private boolean status;

	public Workshop(String name, Map<String, String> speakerInfo, Event event, int capacity, String description,
			Time hour, String image, boolean status) {
		this.name = name;
		this.speakerInfo = speakerInfo;
		this.event = event;
		this.capacity = capacity;
		this.description = description;
		this.hour = hour;
		this.image = image;
		this.status = status;
	}

}
