package com.lmlasmo.shrul.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {

	@JsonProperty(value = "first_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String firstName;
	
	@JsonProperty(value = "last_name", required = false)
	@Size(min = 1)
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String lastName;

}
