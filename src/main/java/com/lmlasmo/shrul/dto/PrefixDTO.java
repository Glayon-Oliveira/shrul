package com.lmlasmo.shrul.dto;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrefixDTO {

	@JsonProperty
	private BigInteger id;

	@JsonProperty
	private String prefix;

}
