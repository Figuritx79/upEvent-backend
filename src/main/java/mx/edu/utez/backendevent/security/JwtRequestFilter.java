package mx.edu.utez.backendevent.security;

import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.backendevent.security.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private final static Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

	private final UserDetailsServiceImpl userDetailsServiceImpl;

	private final JwtUtil jwtUtil;

	@Autowired
	public JwtRequestFilter(UserDetailsServiceImpl userDetailsServiceImpl, JwtUtil jwtUtil) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final var tokenAuthorization = request.getCookies();
		String email = null;
		String jwt = null;

		if (tokenAuthorization != null) {
			for (Cookie cookie : tokenAuthorization) {
				if ("access_token".equals(cookie.getName())) {
					System.out.println(cookie);
					jwt = cookie.getValue();
					break;
				}
			}
		}

		if (jwt != null) {
			try {
				email = jwtUtil.extractUsername(jwt);
			} catch (Exception e) {
				logger.error("Error al extraer el nombre de usuario del token: " + e.getMessage());
			}
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(email);

			if (jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
