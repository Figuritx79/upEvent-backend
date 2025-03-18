package mx.edu.utez.backendevent.workshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.speaker.model.Speaker;

import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "workshop")
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

	@ManyToOne
	@JoinColumn(name = "id_event", nullable = false)
	private Event event;

	@Column(name = "capacity")
	private int capacity;

	@Column(name = "description", columnDefinition = "TINYTEXT")
	private String description;

	@Column(name = "hour")
	private Time hour;

	@Column(name = "image", length = 255)
	private String image;

	@ManyToOne
	@JoinColumn(name = "id_speaker")
	private Speaker speaker;
}
