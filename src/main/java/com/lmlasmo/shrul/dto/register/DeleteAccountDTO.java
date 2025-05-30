package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.auth.CodeHashDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeleteAccountDTO {

	@JsonProperty
	@NotBlank
	private String password;

	@JsonProperty("codeHash")
	@JsonAlias("code_hash")
	@NotNull
	@Valid
	private CodeHashDTO codeHash;

}
