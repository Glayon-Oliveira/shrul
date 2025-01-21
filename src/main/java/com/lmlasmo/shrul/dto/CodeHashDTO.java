package com.lmlasmo.shrul.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeHashDTO {

	@JsonProperty	
	private String hash;
	
	public CodeHashDTO(String hash) {
		this.hash = hash;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}	
	
}
