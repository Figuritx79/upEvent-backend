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
						// .requestMatchers("/api/auth/login", "/api/up",
						// "/api/user/register-admin-event",
						// "/api/auth/recovery-password", "/api/token/valid",
						// "/api/auth/reset-password",
						// "/api/user/checkers", "/api/landing-page/landing/**",
						// "/api/workshop/event/**",
						// "/api/registration/**")
						// .permitAll()
						.requestMatchers("/api/checker/assign", "api/event/").permitAll()
						.requestMatchers("/api/auth/login", "/api/up", "/api/user/register-admin-event",
								"/api/auth/recovery-password", "/api/auth/reset-password",
								"/api/landing-page/landing/**", "/api/workshop/event/**",
								"/api/registration/event-register", "/api/event/events","/api/event")
						.permitAll()
						.requestMatchers("/api/intersection/suscribe")
						.hasAnyAuthority("SUPER_ADMIN")
						.requestMatchers("/api/event/own", "/api/landing-page/landing/create/", "/api/workshop/status", "/api/landing-page/landing/event/{event}",
								"/api/workshop/workshops/create", "/api/event/event", " /api/event/events-update",
								"/api/event/events-delete/**", "/api/landing-page/landing/create/**",
								"/api/checker/create", "api/event/**", "api/checker/event/{id}", "api/checker/reassign")
						.hasAnyAuthority("ADMIN_EVENTO")
						// .requestMatchers("/api/event/events", "/api/event/events/**",
						// "/api/user/update")
						// .hasRole("NORMAL")
						// .requestMatchers("/qr/send", "/api/event/events", "/api/event/events/**",
						// "/api/user/profile",
						// "/api/user/update")
						// .hasAnyAuthority("CHECADOR")
						.requestMatchers("/api/user/profile", "/api/event/events/**", "/api/user/update",
								"/api/qr/send",
								"/api/registration/event-register/movil ", "/api/registration/own",
								"/api/registration/workshop-register", "/api/registration/all/{id}",
								"/api/workshop/workshop/**", " /api/workshop/event/**",
								"/api/workshop/event/**", "/api/registration/participants/**", "/api/checker/assigned",
								"/api/checker/own", "/api/user/update-password", "/api/user/update-profile", "/api/user-workshops/by-email", "/api/user-workshops/my-workshops",
								"/api/checker/register", "/api//participant/register", "/api/occupation/", "/api/occupation", "/api/occupation/own", "/api/registration/own",
								"/api/user-workshops/validate-attendance", "/api/registration/register-participant", "/api/user-workshops/workshop/users", "api/landing-page/landing/update")
						.hasAnyAuthority("NORMAL", "CHECADOR", "ADMIN_EVENTO", "SUPER_ADMIN")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.cors(
						cors -> cors
								.configurationSource(request -> {
									CorsConfiguration config = new CorsConfiguration();
									config.setAllowCredentials(true);
									config.addAllowedOrigin("http://localhost:5173");
									config.addAllowedOrigin("exp://192.168.0.71:8081");
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
