package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SignupDTO {

	@JsonProperty
	@Email
	@NotBlank
	private String email;	
	
	@JsonProperty
	@NotBlank
	@Size(min = 8, max = 255)
	private String password;
	
	@JsonProperty("fist_name")	
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String fistName;
	
	@JsonProperty("last_name")
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String lastName;	
	
	public SignupDTO() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName.toUpperCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}	
	
}
