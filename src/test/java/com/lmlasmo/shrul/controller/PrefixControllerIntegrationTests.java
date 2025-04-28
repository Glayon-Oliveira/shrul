package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmlasmo.shrul.dto.register.RegisterPrefixDTO;
import com.lmlasmo.shrul.dto.update.PrefixUpdateDTO;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.testsetup.AbstractPrefixControllerTestSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PrefixControllerIntegrationTests extends AbstractPrefixControllerTestSetup{

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.prefix.PrefixProvider#provider")
	void registerPrefix(String prefix, int status) throws JsonProcessingException, Exception {
		RegisterPrefixDTO rPrefix = new RegisterPrefixDTO();
		rPrefix.setPrefix(prefix);

		mock.perform(MockMvcRequestBuilders.post("/api/prefix")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(rPrefix)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.prefix.PrefixUpdateProvider#provider")
	void updatePrefix(BigInteger prefixId, String prefix, int status) throws JsonProcessingException, Exception {
		if(prefixId == null) prefixId = this.prefix.getId();

		if(prefix == null) prefix = this.prefix.getPrefix();

		PrefixUpdateDTO update = new PrefixUpdateDTO();
		update.setId(prefixId);
		update.setPrefix(prefix);

		mock.perform(MockMvcRequestBuilders.put("/api/prefix")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(update)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.prefix.PrefixDeleteProvider#provider")
	void deletePrefix(BigInteger prefixId, int status) throws Exception {
		if(prefixId == null) prefixId = this.prefix.getId();

		if(prefixId == BigInteger.ZERO) prefixId = this.user.getPrefixes().toArray(Prefix[]::new)[0].getId();

		mock.perform(MockMvcRequestBuilders.delete("/api/prefix?id="+prefixId)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

}
