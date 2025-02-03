package com.lmlasmo.shrul.dto.register;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LinkUpdateDTO {

	@JsonProperty
	@NotBlank
	@Size(min = 10)
	private String id;

	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger prefix;

	public LinkUpdateDTO() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getPrefix() {
		return prefix;
	}

	public void setPrefix(BigInteger prefix) {
		this.prefix = prefix;
	}

}
