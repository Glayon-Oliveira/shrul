package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignupDTO extends LoginDTO{
	
	@JsonProperty("first_name")	
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String firstName;
	
	@JsonProperty("last_name")
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String lastName;	
	
	public SignupDTO() {}	

	public String getFirstName() {
		return firstName;
	}

	public void setFistName(String firstName) {
		this.firstName = firstName.toUpperCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}	
	
}
