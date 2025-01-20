package com.lmlasmo.shrul.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.JwtTokenDTO;
import com.lmlasmo.shrul.dto.UserDTO;
import com.lmlasmo.shrul.dto.register.LoginDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.register.UserUpdateDTO;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.service.JwtService;
import com.lmlasmo.shrul.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController{

	private UserService userService;
	private JwtService jwtService;
	private AuthenticationManager manager;	
	
	public UserController(UserService userService, JwtService jwtService, AuthenticationManager manager) {
		this.userService = userService;		
		this.jwtService = jwtService;
		this.manager = manager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtTokenDTO> login(@RequestBody @Valid LoginDTO login){
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
		
		Authentication auth = manager.authenticate(authToken);		
		
		List<String> authotities = auth.getAuthorities().stream().map(a -> a.getAuthority()).toList(); 
		
		String jwtToken = jwtService.gerateToken(auth.getName(), authotities);
		
		return ResponseEntity.ok(new JwtTokenDTO(jwtToken));
	}
		
	@PostMapping("/signup")	
	public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignupDTO signup){
		
		UserDTO user = userService.save(signup);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		}
				
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> update(@RequestBody @Valid UserUpdateDTO update){
		
		UserDTO user = userService.update(update);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		}
				
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/lock")
	public ResponseEntity<Object> setLocked(@RequestParam BigInteger id, @RequestParam boolean locked){
		
		userService.setLocked(id, locked);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<UserDTO> getUser(){
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		return ResponseEntity.ok(new UserDTO(user));		
	}
	
	@GetMapping("/search")
	public Page<UserDTO> findAll(Pageable pageable){
		return userService.getRepository().findAll(pageable)
				.map(u -> new UserDTO(u));
	}
	
}
