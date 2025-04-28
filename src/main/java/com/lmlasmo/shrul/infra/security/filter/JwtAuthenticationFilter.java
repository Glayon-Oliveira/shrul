package com.lmlasmo.shrul.infra.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.repository.UserRepository;
import com.lmlasmo.shrul.service.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private UserRepository repository;
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = findToken(request);

		if(token != null) authenticate(token);

		filterChain.doFilter(request, response);
	}

	private void authenticate(String token) {
		User user = findUser(token);

		if((user == null) || !isAuthenticationPermited(user)) return;

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	private String findToken(HttpServletRequest request) {
		String auth = request.getHeader("Authorization");

		if((auth == null) || !auth.contains("Bearer")) return null;

		auth = auth.replace("Bearer", "");
		auth = auth.replace(" ", "");
		return auth;
	}

	private User findUser(String token) {
		String email = jwtService.getEmail(token);

		if(email == null) return null;

		return repository.findByEmail(email).orElseGet(() -> null);
	}

	private boolean isAuthenticationPermited(User user) {
		return !user.isLocked() && user.isEnabled();
	}

}
