package mx.edu.utez.backendevent.event.model.dtos;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyOwnEventDto {

	String name;

	String description;

	Date startDate;

	Date endDate;

	String frontPage;

	// String getName();

	// String getDescription();

	// Date getStartDate();

	// Date getEndDate();

	// String getFrontPage();
}
