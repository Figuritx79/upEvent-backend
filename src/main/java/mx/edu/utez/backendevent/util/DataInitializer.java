package mx.edu.utez.backendevent.util;

import java.sql.Date;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import mx.edu.utez.backendevent.gender.model.Gender;
import mx.edu.utez.backendevent.gender.model.GenderRepository;
import mx.edu.utez.backendevent.passwordResetToken.model.PasswordResetToken;
import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.role.model.RoleRepository;
import mx.edu.utez.backendevent.user.model.User;
import mx.edu.utez.backendevent.user.model.UserRepository;

@Configuration
public class DataInitializer {

	private final PasswordEncoder passwordEncoder;

	DataInitializer(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

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

			Optional<User> admin = userRepository.findByEmail("20233tn059@utez.edu.mx");
			if (!admin.isPresent()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				var password = encoder.encode("123456789");
				var menGender = new Gender(1);
				var adminRole = new Role(1);
				Date date = new Date(20050520);
				User adminInsert = new User("Juan", "Gonzalez", date, "20233tn059@utez.edu.mx", password,
						menGender, adminRole);
				userRepository.saveAndFlush(adminInsert);
			}

			Optional<User> checker = userRepository.findByEmail("20233tn106@utez.edu.mx");
			if (!checker.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Antonio";
				var lastname = "Gonzalez";
				var email = "20233tn106@utez.edu.mx";
				var password = encoder.encode("123456789");
				var menGender = new Gender(1);
				var adminRole = new Role(4);
				var phone = "7774670489";
				var userChecker = new User();

				userChecker.setEmail(email);
				userChecker.setLastname(lastname);
				userChecker.setName(name);
				userChecker.setPassword(password);
				userChecker.setGender(menGender);
				userChecker.setRole(adminRole);
				userChecker.setPhone(phone);
				userRepository.saveAndFlush(userChecker);
			}

			Optional<User> normal = userRepository.findByEmail("20233tn064@utez.edu.mx");
			if (!normal.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Montserrat";
				var lastname = "Pichardo";
				var email = "20233tn064@utez.edu.mx";
				var password = encoder.encode("123456789");
				var menGender = new Gender(2);
				var adminRole = new Role(3);
				var phone = "7774670489";
				var userChecker = new User();

				userChecker.setEmail(email);
				userChecker.setLastname(lastname);
				userChecker.setName(name);
				userChecker.setPassword(password);
				userChecker.setGender(menGender);
				userChecker.setRole(adminRole);
				userChecker.setPhone(phone);
				userRepository.saveAndFlush(userChecker);
			}

			// if (!userTest.isPresent()) {
			// User user = new User();
			// }

		};
	}
}
