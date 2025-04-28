package com.lmlasmo.shrul.testsetup;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmlasmo.shrul.dto.UserDTO;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;
import com.lmlasmo.shrul.dto.register.SignupDTO;
import com.lmlasmo.shrul.model.User;
import com.lmlasmo.shrul.model.UserRole;
import com.lmlasmo.shrul.service.UserService;
import com.lmlasmo.shrul.service.auth.EmailCodeService;
import com.lmlasmo.shrul.service.auth.JwtService;

public abstract class AbstractUserControllerTestSetup extends AbstractBaseControllerTestSetup{

	@Autowired protected MockMvc mock;
	@Autowired protected UserService userService;
	@Autowired protected JwtService jwtService;
	@Autowired protected EmailCodeService emailCodeService;
	@Autowired protected ObjectMapper jMapper;

	protected final String password = UUID.randomUUID().toString();
	protected User user;
	protected CodeHashDTO codeHash;
	protected String jwtToken;

	@BeforeEach
	void setUpUser() {
		userService.getRepository().deleteAll();

		SignupDTO signup = new SignupDTO();
		signup.setEmail("test@gmail.com");
		signup.setFirstName("Testador");
		signup.setLastName("Qrepu");
		signup.setPassword(password);

		UserDTO user = userService.save(signup);
		this.user = userService.getRepository().findByEmail(user.getEmail()).get();

		codeHash = emailCodeService.create(user.getEmail());
		jwtToken = jwtService.gerateToken(user.getEmail(), List.of(UserRole.ROLE_ADMIN.name()));
	}

}
