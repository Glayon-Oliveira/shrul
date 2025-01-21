package com.lmlasmo.shrul.dto.register;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PrefixUpdateDTO {

	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger id;
	
	@JsonProperty	
	@NotBlank
	@Size(min = 1, max = 15)
	@Pattern(regexp = "^[a-z0-9]+$")
	private String prefix;	
	
	public PrefixUpdateDTO() {}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
