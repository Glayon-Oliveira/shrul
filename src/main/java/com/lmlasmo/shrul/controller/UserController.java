package com.lmlasmo.shrul.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lmlasmo.shrul.dto.UserDTO;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;
import com.lmlasmo.shrul.dto.auth.EmailDTO;
import com.lmlasmo.shrul.dto.auth.JwtTokenDTO;
import com.lmlasmo.shrul.dto.register.DeleteAccountDTO;
import com.lmlasmo.shrul.dto.register.LoginDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.update.PasswordUpdateDTO;
import com.lmlasmo.shrul.dto.update.UserUpdateDTO;
import com.lmlasmo.shrul.infra.security.AuthenticatedUser;
import com.lmlasmo.shrul.service.UserService;
import com.lmlasmo.shrul.service.auth.EmailCodeService;
import com.lmlasmo.shrul.service.auth.JwtService;
import com.lmlasmo.shrul.service.email.EmailService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@ResponseStatus(code = HttpStatus.OK)
public class UserController{

	private UserService userService;
	private JwtService jwtService;
	private EmailService emailService;
	private EmailCodeService emailCodeService;
	private AuthenticationManager manager;	

	@PostMapping("/login")
	@ResponseBody
	public JwtTokenDTO login(@RequestBody @Valid LoginDTO login){
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
		Authentication auth = manager.authenticate(authToken);
		List<String> authotities = auth.getAuthorities().stream().map(a -> a.getAuthority()).toList();
		String jwtToken = jwtService.gerateToken(auth.getName(), authotities);
		return new JwtTokenDTO(jwtToken);
	}

	@PostMapping("/signup")
	@ResponseBody
	public UserDTO signup(@RequestBody @Valid SignupDTO signup){
		CodeHashDTO codeHash = signup.getCodeHash();
		emailCodeService.confirm(signup.getEmail(), codeHash);
		return userService.save(signup);
	}

	@PostMapping("/send_code")
	@ResponseBody
	public CodeHashDTO sendCode(@RequestBody @Valid EmailDTO emailDTO){
		String email = emailDTO.getEmail();
		CodeHashDTO codeHash = emailCodeService.create(email);
		String code = codeHash.getCode();
		codeHash.cleanCode();
		
		emailService.send(email, "Confirmar Conta", code);
		return codeHash;
	}

	@PutMapping
	@ResponseBody
	public UserDTO update(@RequestBody @Valid UserUpdateDTO update){
		return userService.update(update, AuthenticatedUser.getUserId());				
	}

	@DeleteMapping	
	public Void delete(@RequestBody @Valid DeleteAccountDTO delete){
		emailCodeService.confirm(AuthenticatedUser.getUserEmail(),  delete.getCodeHash());
		userService.delete(delete, AuthenticatedUser.getUserId());
		return null;
	}

	@PutMapping("/password")
	@ResponseBody
	public UserDTO updatePassword(@RequestBody @Valid PasswordUpdateDTO update){
		emailCodeService.confirm(update.getEmail(), update.getCodeHash());
		return userService.updatePassword(update.getEmail(), update.getPassword());		
	}

	@PutMapping("/lock")	
	@PreAuthorize("hasRole('ADMIN')")
	public Void setLocked(@RequestParam BigInteger id, @RequestParam boolean locked){
		userService.setLocked(id, locked);
		return null;
	}

	@GetMapping
	@ResponseBody
	public UserDTO getUser(){
		return userService.getMapper().userToDTO(AuthenticatedUser.getUser());
	}

}
