package com.lmlasmo.shrul.dto.register;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {
	
	@JsonProperty(required = false)	
	private BigInteger id;	
	
	@JsonProperty(value = "first_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String firstName;
	
	@JsonProperty(value = "last_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String lastName;
	
	public UserUpdateDTO(){}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

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
