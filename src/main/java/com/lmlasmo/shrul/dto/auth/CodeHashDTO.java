package com.lmlasmo.shrul.dto.auth;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CodeHashDTO {

	@JsonProperty
	@NotNull
	private LocalDateTime timestamp;

	@JsonProperty
	@NotBlank
	private String hash;

	@JsonProperty
	@NotBlank
	@Size(min = 6, max = 6)
	private String code;	

	public void cleanCode() {		
		this.code = null;		
	}	

}
