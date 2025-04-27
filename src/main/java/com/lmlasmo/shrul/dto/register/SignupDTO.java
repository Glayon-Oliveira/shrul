package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupDTO extends LoginDTO{

	@JsonProperty("firstName")
	@JsonAlias("first_name")
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String firstName;

	@JsonProperty("lastName")
	@JsonAlias("last_name")
	@NotBlank
	@Pattern(regexp = "^(?!\s).*[^\\s]$")
	private String lastName;

	@JsonProperty("codeHash")
	@JsonAlias("code_hash")
	@NotNull
	@Valid
	private CodeHashDTO codeHash;

}
