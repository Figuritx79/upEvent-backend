package mx.edu.utez.backendevent.util;

import java.sql.Date;
import java.util.Optional;

import mx.edu.utez.backendevent.event.model.Event;
import mx.edu.utez.backendevent.event.model.EventRepository;
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
	private final EventRepository eventRepository;

	DataInitializer(PasswordEncoder passwordEncoder, EventRepository eventRepository) {
		this.passwordEncoder = passwordEncoder;
		this.eventRepository = eventRepository;
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

			Optional<User> normal8 = userRepository.findByEmail("juan.perez@example.com");
			if (!normal.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Juan";
				var lastname = "Pérez";
				var email = "juan.perez@example.com";
				var password = encoder.encode("password1");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0101";
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

			Optional<User> normal9 = userRepository.findByEmail("20233tn077@utez.edu.mx");
			if (!normal.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Diego";
				var lastname = "Amador";
				var email = "20233tn077@utez.edu.mx";
				var password = encoder.encode("20233tn077");
				var company = "UTEZ";
				var adminRole = new Role(2);
				var phone = "7772991476";
				var userChecker = new User();

				userChecker.setEmail(email);
				userChecker.setLastname(lastname);
				userChecker.setName(name);
				userChecker.setPassword(password);
				userChecker.setRole(adminRole);
				userChecker.setPhone(phone);
				userChecker.setCompanyName(company);
				userRepository.saveAndFlush(userChecker);
			}

			Optional<User> normal2 = userRepository.findByEmail("ana.gomez@example.com");
			if (!normal2.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Ana";
				var lastname = "Gómez";
				var email = "ana.gomez@example.com";
				var password = encoder.encode("password2");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0102";
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

			Optional<User> normal3 = userRepository.findByEmail("luis.martinez@example.com");
			if (!normal3.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Luis";
				var lastname = "Martínez";
				var email = "luis.martinez@example.com";
				var password = encoder.encode("password3");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0103";
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

			Optional<User> normal4 = userRepository.findByEmail("carlos.lopez@example.com");
			if (!normal4.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Carlos";
				var lastname = "López";
				var email = "carlos.lopez@example.com";
				var password = encoder.encode("password4");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0104";
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

			Optional<User> normal5 = userRepository.findByEmail("marta.sanchez@example.com");
			if (!normal5.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Marta";
				var lastname = "Sánchez";
				var email = "marta.sanchez@example.com";
				var password = encoder.encode("password5");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0105";
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

			Optional<User> normal6 = userRepository.findByEmail("pedro.rodriguez@example.com");
			if (!normal6.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Pedro";
				var lastname = "Rodríguez";
				var email = "pedro.rodriguez@example.com";
				var password = encoder.encode("password6");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0106";
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

			Optional<User> normal7 = userRepository.findByEmail("laura.diaz@example.com");
			if (!normal7.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "Laura";
				var lastname = "Díaz";
				var email = "laura.diaz@example.com";
				var password = encoder.encode("password7");
				var menGender = new Gender(1);
				var adminRole = new Role(3);
				var phone = "555-0107";
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

			Optional<User> adminDefault = userRepository.findByEmail("20233tn077@utez.edu.mx");
			if (!normal.isPresent()) {
				var encoder = new BCryptPasswordEncoder();
				var name = "default";
				var lastname = "default";
				var email = "default@gmail.com";
				var password = encoder.encode("default");
				var company = "default";
				var adminRole = new Role(2);
				var phone = "7775559991";
				var userChecker = new User();

				userChecker.setEmail(email);
				userChecker.setLastname(lastname);
				userChecker.setName(name);
				userChecker.setPassword(password);
				userChecker.setRole(adminRole);
				userChecker.setPhone(phone);
				userChecker.setCompanyName(company);
				userRepository.saveAndFlush(userChecker);
			}

			Optional<User> eventAdmin = userRepository.findByEmail("default@gmail.com");

			if (eventAdmin.isPresent() && !eventRepository.findByName("Evento por default-1458-jais@/*5").isPresent()) {
				Date startDate = Date.valueOf("2024-03-15");
				Date endDate = Date.valueOf("2024-03-17");

				Event techEvent = new Event(
						"Evento por default-1458-jais@/*5",
						"Evento por default",
						startDate,
						endDate,
						"", // Imagen vacía
						eventAdmin.get()
				);

				eventRepository.saveAndFlush(techEvent);
			}
		};
	}
}
