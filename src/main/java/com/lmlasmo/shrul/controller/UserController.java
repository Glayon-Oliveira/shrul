package com.lmlasmo.shrul.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.lmlasmo.shrul.dto.CodeHashDTO;
import com.lmlasmo.shrul.dto.EmailDTO;
import com.lmlasmo.shrul.dto.JwtTokenDTO;
import com.lmlasmo.shrul.dto.model.UserDTO;
import com.lmlasmo.shrul.dto.register.LoginDTO;
import com.lmlasmo.shrul.dto.register.SignupWithCodeHashDTO;
import com.lmlasmo.shrul.dto.register.UserUpdateDTO;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.service.EmailService;
import com.lmlasmo.shrul.service.JwtService;
import com.lmlasmo.shrul.service.UserService;
import com.lmlasmo.shrul.util.EmailCodeTool;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController{

	private UserService userService;
	private JwtService jwtService;
	private EmailService emailService;
	private AuthenticationManager manager;

	@Autowired
	public UserController(UserService userService, JwtService jwtService, EmailService emailService, AuthenticationManager manager) {
		this.userService = userService;
		this.jwtService = jwtService;
		this.emailService = emailService;
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
	public ResponseEntity<UserDTO> signup(@RequestBody @Valid SignupWithCodeHashDTO signup){

		String code = signup.getCode();
		String hash = signup.getHash();

		if(!EmailCodeTool.confirm(signup.getEmail(), code, hash)) {
			return ResponseEntity.badRequest().build();
		}

		UserDTO user = userService.save(signup);

		if(user != null) {
			return ResponseEntity.ok(user);
		}

		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/send_code")
	public ResponseEntity<CodeHashDTO> sendCode(@RequestBody @Valid EmailDTO emailDTO){

		String email = emailDTO.getEmail();

		if(userService.getRepository().existsByEmail(email)) {
			return ResponseEntity.badRequest().build();
		}

		Map<String,String> codeHash = EmailCodeTool.create(email);

		String code = codeHash.get("code");
		String hash = codeHash.get("hash");

		emailService.send(email, "Confirmar Conta", code);

		return ResponseEntity.ok(new CodeHashDTO(hash));
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> setLocked(@RequestParam BigInteger id, @RequestParam boolean locked){

		userService.setLocked(id, locked);

		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<UserDTO> getUser(){

		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return ResponseEntity.ok(new UserDTO(user));
	}

}
