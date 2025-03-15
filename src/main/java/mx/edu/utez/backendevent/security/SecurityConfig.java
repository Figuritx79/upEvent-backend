package mx.edu.utez.backendevent.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	private JwtRequestFilter jwtRequestFilter;

	private Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/nose").hasAuthority("SUPER_ADMIN")
						.requestMatchers("/api/hola").hasAuthority("ADMIN_EVENTO")
						.requestMatchers("/api/pedro").hasAnyAuthority("NORMAL")
						.requestMatchers("/api/megu").hasAnyAuthority("CHECADOR")
						.requestMatchers("/api/auth/login", "/api/auth/register", "/api/user/users", "/up", "/level")
						.permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptions -> exceptions
						.authenticationEntryPoint((request, response, ex) -> {
							log.warn("Errorr de autenticacion" + ex.getMessage() + " " + ex.getLocalizedMessage());
							response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
							response.getWriter().write("No autorizado: " + ex.getMessage());
						})
						.accessDeniedHandler((request, response, ex) -> {
							log.warn("Accesso denegado" + ex.getMessage() + " " + ex.getLocalizedMessage());
							response.setStatus(HttpServletResponse.SC_FORBIDDEN);
							response.getWriter().write("Acceso denegado: " + ex.getMessage());
						}))
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
