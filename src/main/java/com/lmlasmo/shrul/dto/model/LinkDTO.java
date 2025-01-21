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
	private String destine;
	
	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger prefix;
	
	public LinkDTO() {}
	
	public LinkDTO(Link link) {
		this.id = link.getId();
		this.destine = link.getDestine();
		this.prefix = link.getPrefix().getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDestine() {
		return destine;
	}

	public void setDestine(String destine) {
		this.destine = destine;
	}

	public BigInteger getPrefix() {
		return prefix;
	}

	public void setPrefix(BigInteger prefix) {
		this.prefix = prefix;
	}	
	
}
