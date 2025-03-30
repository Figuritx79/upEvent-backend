package mx.edu.utez.backendevent.landingPage.model.dtos;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonFactory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateLandingPageDto {

	private MultipartFile logo;
	private MultipartFile gallery1;
	private MultipartFile gallery2;
	private MultipartFile gallery3;
	private String eventName;
}
