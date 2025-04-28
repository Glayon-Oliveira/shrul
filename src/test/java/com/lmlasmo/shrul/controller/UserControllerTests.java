package com.lmlasmo.shrul.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmlasmo.shrul.dto.CodeHashDTO;
import com.lmlasmo.shrul.dto.JwtTokenDTO;
import com.lmlasmo.shrul.dto.model.UserDTO;
import com.lmlasmo.shrul.dto.register.DeleteAccountDTO;
import com.lmlasmo.shrul.dto.register.LoginDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.update.PasswordUpdateDTO;
import com.lmlasmo.shrul.dto.update.UserUpdateDTO;
import com.lmlasmo.shrul.infra.security.EmailCodeTool;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.model.UserRole;
import com.lmlasmo.shrul.service.JwtService;
import com.lmlasmo.shrul.service.UserService;

@Rollback
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
		
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private EmailCodeTool emailCodeTool;
	
	@Autowired
	private ObjectMapper jMapper;	
	
	private String password = UUID.randomUUID().toString();
	
	private UserDTO user;
	private CodeHashDTO codeHash;
	private String jwtToken;
	
	@BeforeEach
	void setup() {		
		userService.getRepository().deleteAll();		
		
		SignupDTO signup = new SignupDTO();
		signup.setEmail("test@gmail.com");
		signup.setFirstName("Testador");
		signup.setLastName("Qrepu");
		signup.setPassword(password);
		
		user = userService.save(signup);				
		codeHash = emailCodeTool.create(signup.getEmail());
		jwtToken = jwtService.gerateToken(signup.getEmail(), List.of(UserRole.ROLE_ADMIN.name()));
	}
	
	@Test
	void signupTest() throws JsonProcessingException, Exception {		
		SignupDTO signup = new SignupDTO();
		signup.setEmail("test1@gmail.com");
		signup.setFirstName("Testador");
		signup.setLastName("Qrepu");
		signup.setPassword(password);
		signup.setCodeHash(emailCodeTool.create(signup.getEmail()));
		
		mock.perform(MockMvcRequestBuilders.post("/user/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(signup)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void testLogin() throws JsonProcessingException, UnsupportedEncodingException, Exception {
		String uri = "/user/login";		
		
		LoginDTO login = new LoginDTO();
		login.setEmail("faiure@gmail.com");
		login.setPassword("password");
		
		mock.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(login)))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
				
		login.setEmail(user.getEmail());
		login.setPassword(password);
		System.out.println("===============");
		String jwt = mock.perform(MockMvcRequestBuilders.post(uri)
						.contentType(MediaType.APPLICATION_JSON)
						.content(jMapper.writeValueAsString(login)))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andReturn().getResponse().getContentAsString();
		
		JwtTokenDTO token = jMapper.readValue(jwt, JwtTokenDTO.class);
		
		uri = "/user";
		
		mock.perform(MockMvcRequestBuilders.get(uri)
				.header("Authorization", "Bearer " + token.getJwtToken()))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
	}
	
	@Test
	void updateTest() throws JsonProcessingException, Exception {
		PasswordUpdateDTO updatePassword = new PasswordUpdateDTO();		
		updatePassword.setEmail(user.getEmail());
		updatePassword.setPassword(UUID.randomUUID().toString());
		updatePassword.setCodeHash(codeHash);
		
		mock.perform(MockMvcRequestBuilders.put("/user/password")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(updatePassword)))
		.andExpect(MockMvcResultMatchers.status().isOk());
		
		password = updatePassword.getPassword();
		
		UserUpdateDTO update = new UserUpdateDTO();
		update.setFirstName("Teste2");
		update.setLastName("QUI");
		
		mock.perform(MockMvcRequestBuilders.put("/user")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(update)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(update.getFirstName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(update.getLastName()));
	}	
	
	@Test
	void lockAndRoleTest() throws Exception {
		User user = userService.getRepository().findByEmail(this.user.getEmail()).orElseGet(() -> null);
		
		String uri = "/user/lock?id=%s&locked=%b";
		uri = String.format(uri, user.getId(), true);
		
		mock.perform(MockMvcRequestBuilders.put(uri)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().isForbidden());
		
		user.setRole(UserRole.ROLE_ADMIN);
		userService.getRepository().save(user);
		
		mock.perform(MockMvcRequestBuilders.put(uri)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().isOk());		
		
		mock.perform(MockMvcRequestBuilders.get("/user")
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		
		user.setLocked(false);
		userService.getRepository().save(user);
	}
	
	@AfterEach
	void deleteTest() throws JsonProcessingException, Exception {		
		DeleteAccountDTO delete = new DeleteAccountDTO();
		delete.setPassword(password);
		delete.setCodeHash(emailCodeTool.create(user.getEmail()));
		
		mock.perform(MockMvcRequestBuilders.delete("/user")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(delete)))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
