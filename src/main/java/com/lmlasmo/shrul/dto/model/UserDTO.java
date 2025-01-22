package com.lmlasmo.shrul.dto.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.User;

public class UserDTO {

	@JsonProperty
	private String email;
	
	@JsonProperty("first_name")		
	private String firstName;
	
	@JsonProperty("last_name")	
	private String lastName;
	
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	public UserDTO() {}

	public UserDTO(User user) {		
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.createdAt = user.getCreatedAt();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
