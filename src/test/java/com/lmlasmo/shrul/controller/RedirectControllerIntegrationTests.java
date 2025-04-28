package com.lmlasmo.shrul.controller;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.lmlasmo.shrul.testsetup.AbstractLinkControllerTestSetup;

public class RedirectControllerIntegrationTests extends AbstractLinkControllerTestSetup {

	@ParameterizedTest
	@MethodSource("com.lmlasmo.shrul.data.redirect.RedirectProvider#provider")
	public void redirectLink(String segmentPrefix, String segmentLinkId, int status) throws Exception {
		if(segmentPrefix == null) segmentPrefix = "/" + this.prefix.getPrefix();

		if(segmentLinkId == null) segmentLinkId = "/" + this.link.getId();

		mock.perform(MockMvcRequestBuilders.get(segmentPrefix + segmentLinkId)
				.header("Authorization", "Bearer " + jwtToken))
		.andExpect(MockMvcResultMatchers.status().is(status));

		if(segmentPrefix.equals(this.prefix.getPrefix()) && segmentLinkId.equals(this.link.getId())) {
			mock.perform(MockMvcRequestBuilders.get("/url_access")
					.header("Authorization", "Bearer " + jwtToken))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(1));
		}
	}

}
