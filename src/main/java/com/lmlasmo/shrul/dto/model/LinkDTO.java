package com.lmlasmo.shrul.dto.model;

import java.math.BigInteger;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmlasmo.shrul.model.Link;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LinkDTO {

	@JsonProperty(required = false)	
	private String id;
	
	@JsonProperty
	@NotBlank
	@URL
	private String destination;
	
	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger prefix;
	
	public LinkDTO() {}
	
	public LinkDTO(Link link) {
		this.id = link.getId();
		this.destination = link.getDestination();
		this.prefix = link.getPrefix().getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public BigInteger getPrefix() {
		return prefix;
	}

	public void setPrefix(BigInteger prefix) {
		this.prefix = prefix;
	}	
	
}
