package mx.edu.utez.backendevent.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import jakarta.servlet.http.Part;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CloudinaryUpload {

	@Value("${API_KEY_CLOUDINARY}")
	private String apiKeyCloudinary;

	@Value("${API_SECRET_CLOUDINARY}")
	private String apiSecretCloudinary;

	// private String env = "CLOUDINARY_URL=cloudinary://" + apiKeyCloudinary + ":"
	// + apiSecretCloudinary + "@dt9d7lbhg";

	private Logger logger = LoggerFactory.getLogger(CloudinaryUpload.class);

	public String UploadImage(MultipartFile image) throws IOException {
		String url = "";
		var imageName = image.getOriginalFilename();
		var imageByteArray = imageName.getBytes();
		var config = ObjectUtils.asMap(
				"cloud_name", "dt9d7lbhg",
				"api_key", apiKeyCloudinary,
				"api_secret", apiSecretCloudinary,
				"public_id", imageName,
				"resource_type", "image");
		try {
			Cloudinary cloudinary = new Cloudinary();
			var result = cloudinary.uploader().upload(imageByteArray, config);

			url = result.get("url").toString();
			logger.info("IMAGE SUCCES UPLOAD");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("IMAGE ERROR UPLOAD");
		}
		return url;
	}

	private byte[] imageToByteArray(Part image) throws IOException {
		try (InputStream inputStream = image.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

			int nRead;
			byte[] data = new byte[1024]; // Tamaño del buffer

			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			return buffer.toByteArray();
		}
	}
}
