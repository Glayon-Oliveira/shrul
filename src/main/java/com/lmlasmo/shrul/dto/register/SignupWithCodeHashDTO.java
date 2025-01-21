package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

public class SignupWithCodeHashDTO extends SignupDTO {

	@JsonProperty
	@NotBlank
	private String code;

	@JsonProperty
	@NotBlank
	private String hash;

	public SignupWithCodeHashDTO() {}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
