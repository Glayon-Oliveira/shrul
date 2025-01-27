package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.CodeHashDTO;
import com.lmlasmo.shrul.dto.EmailDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PasswordUpdateDTO extends EmailDTO{

	@JsonProperty
	@NotBlank
	@Size(min = 8, max = 255)
	private String password;
	
	@JsonProperty("code_hash")
	@NotNull
	private CodeHashDTO codeHash;	
	
	public PasswordUpdateDTO() {}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CodeHashDTO getCodeHash() {
		return codeHash;
	}

	public void setCodeHash(CodeHashDTO codeHash) {
		this.codeHash = codeHash;
	}	
	
}
