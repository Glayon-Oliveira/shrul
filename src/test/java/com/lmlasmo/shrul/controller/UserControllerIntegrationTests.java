package com.lmlasmo.shrul.controller;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;
import com.lmlasmo.shrul.dto.auth.JwtTokenDTO;
import com.lmlasmo.shrul.dto.register.DeleteAccountDTO;
import com.lmlasmo.shrul.dto.register.LoginDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.dto.update.PasswordUpdateDTO;
import com.lmlasmo.shrul.dto.update.UserUpdateDTO;
import com.lmlasmo.shrul.model.UserRole;
import com.lmlasmo.shrul.testsetup.AbstractUserControllerTestSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerIntegrationTests extends AbstractUserControllerTestSetup{

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.SignupProvider#provider")
	void signupUser(String email, String fistName, String lastName, String password, CodeHashDTO codeHash, int status) throws JsonProcessingException, Exception {
		if(password == null) password = this.password;

		if(codeHash == null) codeHash = emailCodeService.create(email.toUpperCase());

		SignupDTO signup = new SignupDTO();
		signup.setEmail(email);
		signup.setFirstName(fistName);
		signup.setLastName(lastName);
		signup.setPassword(password);
		signup.setCodeHash(codeHash);

		mock.perform(MockMvcRequestBuilders.post("/api/user/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(signup)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.LoginProvider#provider")
	void loginUser(String email, String password, int status) throws JsonProcessingException, UnsupportedEncodingException, Exception {
		if(email == null) email = user.getEmail();

		if(password == null) password = this.password;

		LoginDTO login = new LoginDTO();
		login.setEmail(email);
		login.setPassword(password);

		ResultActions result = mock.perform(MockMvcRequestBuilders.post("/api/user/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(login)));

		result.andExpect(MockMvcResultMatchers.status().is(status));

		if(status == 200) {
			String token = result.andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken").exists())
					.andReturn().getResponse().getContentAsString();

			JwtTokenDTO tokenDTO = jMapper.readValue(token, JwtTokenDTO.class);

			mock.perform(MockMvcRequestBuilders.get("/api/user")
					.header("Authorization", "Bearer " + tokenDTO.getJwtToken()))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
		}

	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.PasswordUpdateProvider#provider")
	void updateUserPassword(String password, int status) throws JsonProcessingException, Exception {
		if(password == null) password = this.password;

		PasswordUpdateDTO updatePassword = new PasswordUpdateDTO();
		updatePassword.setEmail(user.getEmail());
		updatePassword.setPassword(password);
		updatePassword.setCodeHash(codeHash);

		mock.perform(MockMvcRequestBuilders.put("/api/user/password")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(updatePassword)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.UserUpdateProvider#provider")
	void updateUser(String firstName, String lastName, int status) throws JsonProcessingException, Exception {
		UserUpdateDTO update = new UserUpdateDTO();
		update.setFirstName(firstName);
		update.setLastName(lastName);

		mock.perform(MockMvcRequestBuilders.put("/api/user")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(update)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.LockedProvider#provider")
	void setUserLock(UserRole role, Boolean locked, int status) throws Exception {
		user.setRole(role);
		userService.getRepository().saveAndFlush(user);

		String uri = String.format("/api/user/lock?id=%s&locked=%b", user.getId(), locked);

		if(locked == null) uri = uri.substring(0, uri.indexOf("locked"));

		mock.perform(MockMvcRequestBuilders.put(uri)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().is(status));

		if(locked == null) return;

		if(locked && status == 200) {
			mock.perform(MockMvcRequestBuilders.get("/api/user")
					.header("Authorization", "Bearer " + jwtToken))
			.andExpect(MockMvcResultMatchers.status().isUnauthorized());
		}
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.user.AccountDeleteProvider#provider")
	void deleteUserAccount(String password, CodeHashDTO codeHash, int status) throws JsonProcessingException, Exception {
		if(password == null) password = this.password;

		if(codeHash == null) codeHash = this.codeHash;

		DeleteAccountDTO delete = new DeleteAccountDTO();
		delete.setPassword(password);
		delete.setCodeHash(codeHash);
		mock.perform(MockMvcRequestBuilders.delete("/api/user")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(delete)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

}
