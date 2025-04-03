package mx.edu.utez.backendevent.event_checker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.user.model.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "event_checker")
@Getter
@Setter
@NoArgsConstructor
public class EventChecker {

	@EmbeddedId
	@JsonIgnore
	private EventCheckerId id;

	@ManyToOne
	@MapsId("idEvent")
	@JoinColumn(name = "id_event", nullable = false)
	private Event event;

	@ManyToOne
	@MapsId("idUser")
	@JoinColumn(name = "id_checker", nullable = false)
	private User checker;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "assigned_by", nullable = false)
	private User assignedBy;
}
