package com.lmlasmo.shrul.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.auth.EmailDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends EmailDTO{	

	@JsonProperty("firstName")
	@JsonAlias("first_name")
	private String firstName;

	@JsonProperty("lastName")
	@JsonAlias("last_name")
	private String lastName;

	@JsonProperty("createdAt")
	@JsonAlias("create_at")
	private LocalDateTime createdAt;

}
