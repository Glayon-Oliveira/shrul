package com.lmlasmo.shrul.dto.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.EmailDTO;
import com.lmlasmo.shrul.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends EmailDTO{	

	@JsonProperty("first_name")		
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;
	
	@JsonProperty("created_at")
	private LocalDateTime createdAt;

	public UserDTO(User user) {		
		this.setEmail(user.getEmail());
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.createdAt = user.getCreatedAt();
	}

}
