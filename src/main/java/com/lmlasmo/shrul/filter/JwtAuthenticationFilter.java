package com.lmlasmo.shrul.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.UserRepository;
import com.lmlasmo.shrul.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private UserRepository repository;
	private JwtService jwtService;

	@Autowired
	public JwtAuthenticationFilter(UserRepository repository, JwtService jwtService) {
		this.repository = repository;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = findToken(request);

		if(token != null) {
			authenticate(token);
		}

		filterChain.doFilter(request, response);
	}

	private void authenticate(String token) {

		Optional<User> userOp = findUser(token);

		if(userOp.isPresent()) {

			User user = userOp.get();

			if(isAuthenticationPermited(user)) {

				UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}

		}

	}

	private String findToken(HttpServletRequest request) {

		String auth = request.getHeader("Authorization");

		if(auth != null) {
			
			if(auth.contains("Bearer ")) {
				auth = auth.replace("Bearer ", "");
			}
						
		}

		return auth;
	}

	private Optional<User> findUser(String token) {

		String email = jwtService.getEmail(token);

		return (email != null) ? repository.findByEmail(email) : Optional.ofNullable(null);
	}

	private boolean isAuthenticationPermited(User user) {
		return !user.isLocked() && user.isEnabled();
	}

}
