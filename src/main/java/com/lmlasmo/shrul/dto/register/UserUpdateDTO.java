package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {

	@JsonProperty(value = "fist_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String fistName;

	@JsonProperty(value = "last_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String lastName;

	public UserUpdateDTO(){}

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
