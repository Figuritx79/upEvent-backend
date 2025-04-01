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
import org.springframework.web.cors.CorsConfiguration;

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
						.requestMatchers("/api/auth/login", "/api/up", "/api/user/register-admin-event",
								"/api/auth/recovery-password", "/api/token/valid", "/api/auth/reset-password",
								"/api/user/checkers", "/api/landing-page/landing/**", "/api/workshop/event/**",
								"/api/registration/**")
						.permitAll()
						.requestMatchers("/api/intersection/suscribe", "/api/user/info/**")
						.hasAnyAuthority("SUPER_ADMIN")
						.requestMatchers("/api/event/**", "/api/landing-page/**", "/api/workshop/**")
						.hasAnyAuthority("ADMIN_EVENTO")
						.requestMatchers("/api/event/events", "/api/event/events/**")
						.hasRole("NORMAL")
						.requestMatchers("/qr/send", "/api/event/events", "/api/event/events/**")
						.hasAnyAuthority("CHECADOR")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(
						cors -> cors
								.configurationSource(request -> {
									CorsConfiguration config = new CorsConfiguration();
									config.setAllowCredentials(true);
									config.addAllowedOrigin("http://localhost:5173");
									config.addAllowedHeader("*");
									config.addAllowedMethod("*");
									return config;
								}))
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
