package com.lmlasmo.shrul.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
	
	public CodeHashDTO() {}

	public CodeHashDTO(LocalDateTime timestamp, String hash, String code) {
		this.timestamp = timestamp;
		this.hash = hash;
		this.code = code;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getCode() {
		return code;
	}
	
	public String getCodeAndSetNull() {
		
		String code = this.code;
		this.code = null;
		
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}	

}
