package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.EmailDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordUpdateWithCodeHashDTO extends EmailDTO{

	@JsonProperty
	@NotBlank
	@Size(min = 8, max = 255)
	private String password;
	
	@JsonProperty
	@NotBlank
	private String hash;
	
	@JsonProperty
	@NotBlank
	private String code;
	
	public PasswordUpdateWithCodeHashDTO() {}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	
	
}
