package com.lmlasmo.shrul.infra.security.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.lmlasmo.shrul.infra.security.filter.JwtAuthenticationFilter;
import com.lmlasmo.shrul.infra.security.handler.AccessDeniedHandlerImpl;
import com.lmlasmo.shrul.infra.security.handler.AuthenticationEntryPointImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEntryPointImpl entryPoint,
			AccessDeniedHandlerImpl deniedHandler, JwtAuthenticationFilter jwtFilter) throws Exception {
		return http.csrf(c -> c.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(f -> f.disable())
				.httpBasic(Customizer.withDefaults())
				.exceptionHandling(e -> e.authenticationEntryPoint(entryPoint)
						.accessDeniedHandler(deniedHandler))
				.authorizeHttpRequests(a -> a.requestMatchers("/api/user/login", "/api/user/signup", "/api/user/send_code", "/api/user/password").permitAll()
						.requestMatchers("/api/**").authenticated()
						.anyRequest().permitAll())
				.addFilterBefore(jwtFilter, BasicAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConf) throws Exception {
		return authenticationConf.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}