package mx.edu.utez.backendevent.event.model.dtos;

import java.sql.Date;
import java.util.UUID;

import org.hibernate.type.descriptor.jdbc.BinaryJdbcType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyOwnEventDto {

	private BinaryJdbcType id;
	private String name;

	private String description;

	private Date startDate;

	private Date endDate;

	private String frontPage;

	// String getName();

	// String getDescription();

	// Date getStartDate();

	// Date getEndDate();

	// String getFrontPage();
}
