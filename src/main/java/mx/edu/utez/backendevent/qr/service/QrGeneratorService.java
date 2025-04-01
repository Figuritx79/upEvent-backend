package mx.edu.utez.backendevent.qr.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.awt.image.BufferedImage;

@Service
public class QrGeneratorService {

	public String generateQrBase64(String data) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 250, 250);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			javax.imageio.ImageIO.write(qrImage, "png", baos);
			byte[] qrBytes = baos.toByteArray();

			return Base64.getEncoder().encodeToString(qrBytes);
		} catch (WriterException | IOException e) {
			throw new RuntimeException("Error al generar el QR", e);
		}
	}

	public String generateQrBase64(UUID idEvent) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(idEvent.toString(), BarcodeFormat.QR_CODE, 250, 250);
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			javax.imageio.ImageIO.write(qrImage, "png", baos);
			byte[] qrBytes = baos.toByteArray();

			return Base64.getEncoder().encodeToString(qrBytes);
		} catch (WriterException | IOException e) {
			throw new RuntimeException("Error al generar el QR", e);
		}
	}
}
