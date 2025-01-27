package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {

	@JsonProperty(value = "first_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String firstName;
	
	@JsonProperty(value = "last_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String lastName;

	public UserUpdateDTO(){}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.toUpperCase();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}

}
