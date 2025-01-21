package com.lmlasmo.shrul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class EmailDTO {

	@JsonProperty
	@NotBlank
	private String email;

	public EmailDTO() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}

}
