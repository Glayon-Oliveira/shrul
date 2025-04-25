package com.lmlasmo.shrul.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {

	@JsonProperty
	@Email
	@NotBlank
	private String email;	

	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}

}
