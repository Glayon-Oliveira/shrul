package com.lmlasmo.shrul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtTokenDTO {

	@JsonProperty("jwt_token")
	private String jwtToken;

	public JwtTokenDTO() {}

	public JwtTokenDTO(String token) {
		this.jwtToken = token;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

}
