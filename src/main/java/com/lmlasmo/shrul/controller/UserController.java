package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.UserDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.register.UserUpdateDTO;
import com.lmlasmo.shrul.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController{

	private UserService service;
	
	public UserController(UserService service) {
		this.service = service;
	}
		
	@PostMapping("/signup")	
	public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignupDTO signup){
		
		UserDTO user = service.save(signup);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		}
				
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping
	public ResponseEntity<UserDTO> update(@RequestBody @Valid UserUpdateDTO update){
		
		UserDTO user = service.update(update);
		
		if(user != null) {
			return ResponseEntity.ok(user);
		}
				
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/lock")
	public ResponseEntity<Object> setLocked(@RequestParam BigInteger id, @RequestParam boolean locked){
		
		service.setLocked(id, locked);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public Page<UserDTO> findAll(Pageable pageable){
		return service.getRepository().findAll(pageable)
				.map(u -> new UserDTO(u));
	}
	
}
