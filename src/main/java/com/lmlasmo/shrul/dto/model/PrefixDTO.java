package com.lmlasmo.shrul.dto.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.Prefix;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class PrefixDTO {

	@JsonProperty(required = false)
	@Min(1)
	private BigInteger id;
	
	@JsonProperty(required = false)
	@Pattern(regexp = "^[a-z]+$")
	private String prefix;
	
	@JsonProperty
	@NotNull	
	@Min(1)
	private BigInteger user;
	
	public PrefixDTO() {}
	
	public PrefixDTO(Prefix prefix) {		
		this.id = prefix.getId();
		this.prefix = prefix.getPrefix();
		this.user = prefix.getUser().getId();		
	}

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

	public BigInteger getUser() {
		return user;
	}

	public void setUser(BigInteger user) {
		this.user = user;
	}	
	
}
