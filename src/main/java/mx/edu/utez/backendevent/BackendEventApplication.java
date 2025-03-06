package mx.edu.utez.backendevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendEventApplication.class, args);
	}

}
