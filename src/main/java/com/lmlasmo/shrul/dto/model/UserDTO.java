package com.lmlasmo.shrul.dto.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.User;

public class UserDTO {

	@JsonProperty
	private String email;

	@JsonProperty("fist_name")
	private String fistName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty
	private LocalDateTime createdAt;

	public UserDTO() {}

	public UserDTO(User user) {
		this.email = user.getEmail();
		this.fistName = user.getFistName();
		this.lastName = user.getLastName();
		this.createdAt = user.getCreatedAt();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
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
