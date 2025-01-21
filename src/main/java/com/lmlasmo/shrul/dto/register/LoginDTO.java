package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.EmailDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginDTO extends EmailDTO{	
	
	@JsonProperty
	@NotBlank
	@Size(min = 8, max = 255)
	private String password;
	
	public LoginDTO() {}	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
}
