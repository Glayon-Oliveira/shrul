package com.lmlasmo.shrul.controller;

import java.math.BigInteger;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lmlasmo.shrul.dto.register.RegisterLinkDTO;
import com.lmlasmo.shrul.dto.update.LinkUpdateDTO;
import com.lmlasmo.shrul.model.Prefix;
import com.lmlasmo.shrul.testsetup.AbstractLinkControllerTestSetup;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LinkControllerIntegrationTests extends AbstractLinkControllerTestSetup{

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.link.LinkProvider#provider")
	public void registerLink(BigInteger prefixId, String destination, int status) throws JsonProcessingException, Exception {
		if(prefixId == null) prefixId = prefix.getId();

		if(prefixId.equals(BigInteger.ZERO)) prefixId = user.getPrefixes().toArray(Prefix[]::new)[0].getId();

		RegisterLinkDTO rLink = new RegisterLinkDTO();
		rLink.setPrefixId(prefixId);
		rLink.setDestination(destination);

		mock.perform(MockMvcRequestBuilders.post("/api/link")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(rLink)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.link.UpdateLinkProvider#provider")
	public void updateLink(String linkId, BigInteger prefixId, int status) throws JsonProcessingException, Exception {
		if(prefixId == null) prefixId = prefix.getId();

		if(prefixId.equals(BigInteger.ZERO)) prefixId = user.getPrefixes().toArray(Prefix[]::new)[0].getId();

		if(linkId == null) linkId = link.getId();

		LinkUpdateDTO update = new LinkUpdateDTO();
		update.setId(linkId);
		update.setPrefixId(prefixId);

		mock.perform(MockMvcRequestBuilders.put("/api/link")
				.header("Authorization", "Bearer " + jwtToken)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jMapper.writeValueAsString(update)))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.link.LinkDeleteProvider#provider")
	public void deleteLink(String linkId, int status) throws Exception {
		if(linkId == null) linkId = link.getId();

		mock.perform(MockMvcRequestBuilders.delete("/api/link?id="+linkId)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().is(status));
	}

}
