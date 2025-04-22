package com.lmlasmo.shrul.dto.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.dto.CodeHashDTO;
import com.lmlasmo.shrul.dto.EmailDTO;

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

	@JsonProperty("code_hash")
	@NotNull
	private CodeHashDTO codeHash;

}
