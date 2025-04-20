package mx.edu.utez.backendevent.landingPage.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLandingPageDto {
	private String id;
	private MultipartFile logo;
	private MultipartFile gallery1;
	private MultipartFile gallery2;
	private MultipartFile gallery3;
	private String eventName;
}
