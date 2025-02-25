package mx.edu.utez.backendevent.util;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import mx.edu.utez.backendevent.role.model.Role;
import mx.edu.utez.backendevent.role.model.RoleRepository;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initDataBase(RoleRepository roleRepository) {
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

        };
    }
}