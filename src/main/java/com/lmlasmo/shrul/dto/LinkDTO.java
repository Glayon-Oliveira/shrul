package com.lmlasmo.shrul.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkDTO {

	@JsonProperty
	private String id;

	@JsonProperty	
	private String destination;

	@JsonProperty("prefixId")
	@JsonAlias("prefix_id")
	private BigInteger prefixId;

}
