package com.lmlasmo.shrul.dto;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.User;

public class UserDTO {
	
	@JsonProperty
	private BigInteger id;

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
		this.id = user.getId();
		this.email = user.getEmail();
		this.fistName = user.getFistName();
		this.lastName = user.getLastName();
		this.createdAt = user.getCreatedAt();
	}	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
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
