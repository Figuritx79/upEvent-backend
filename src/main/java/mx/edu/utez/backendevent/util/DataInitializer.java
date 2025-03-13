package mx.edu.utez.backendevent.util;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.gender.service.GenderService;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.role.model.RoleRepository;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;

@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner initDataBase(RoleRepository roleRepository, UserRepository userRepository,
			GenderRepository genderRepository) {
		return args -> {
			Optional<Role> optionalRole = roleRepository.findRoleByName("SUPER_ADMIN");
			if (!optionalRole.isPresent()) {
				Role role = new Role("SUPER_ADMIN");
				roleRepository.saveAndFlush(role);

			}
			Optional<Role> optionalRole2 = roleRepository.findRoleByName("ADMIN_EVENTO");
			if (!optionalRole2.isPresent()) {
				Role role = new Role("ADMIN_EVENTO");
				roleRepository.saveAndFlush(role);

			}
			Optional<Role> optionalRole3 = roleRepository.findRoleByName("NORMAL");
			if (!optionalRole3.isPresent()) {
				Role role = new Role("NORMAL");
				roleRepository.saveAndFlush(role);

			}
			Optional<Role> optionalRole4 = roleRepository.findRoleByName("CHECADOR");
			if (!optionalRole4.isPresent()) {
				Role role = new Role("CHECADOR");
				roleRepository.saveAndFlush(role);

			}

			Optional<Gender> men = genderRepository.findByName("Hombre");
			if (!men.isPresent()) {
				Gender menInsert = new Gender("Hombre");
				genderRepository.saveAndFlush(menInsert);

			}
			Optional<Gender> woman = genderRepository.findByName("Mujer");
			if (!woman.isPresent()) {
				Gender womanInsert = new Gender("Mujer");
				genderRepository.saveAndFlush(womanInsert);

			}
			// Optional<User> userTest =
			// userRepository.getUserByEmail("gonherenrique@gmail.com");

			// if (!userTest.isPresent()) {
			// User user = new User();
			// }

		};
	}
}
