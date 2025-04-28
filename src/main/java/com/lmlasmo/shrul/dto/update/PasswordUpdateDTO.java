package com.lmlasmo.shrul.dto.update;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;
import com.lmlasmo.shrul.dto.auth.EmailDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordUpdateDTO extends EmailDTO{

	@JsonProperty
	@NotBlank
	@Size(min = 8, max = 255)
	private String password;

	@JsonProperty("codeHash")
	@JsonAlias("code_hash")
	@NotNull
	@Valid
	private CodeHashDTO codeHash;

}
