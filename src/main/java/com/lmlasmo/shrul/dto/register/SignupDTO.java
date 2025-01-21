package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class SignupDTO extends LoginDTO{	
	
	@JsonProperty("fist_name")	
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String fistName;
	
	@JsonProperty("last_name")
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	public String lastName;	
	
	public SignupDTO() {}	

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
