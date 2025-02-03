package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.CodeHashDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeleteAccountDTO {

	@JsonProperty
	@NotBlank
	private String password;

	@JsonProperty("hash_code")
	@NotNull
	@Valid
	private CodeHashDTO hashCode;

	public DeleteAccountDTO() {}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CodeHashDTO getHashCode() {
		return hashCode;
	}

	public void setHashCode(CodeHashDTO hashCode) {
		this.hashCode = hashCode;
	}

}
