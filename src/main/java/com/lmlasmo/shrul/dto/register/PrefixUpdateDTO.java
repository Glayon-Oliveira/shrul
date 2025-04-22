package com.lmlasmo.shrul.dto.register;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrefixUpdateDTO {

	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger id;

	@JsonProperty
	@Pattern(regexp = "^[A-Za-z]+$")
	private String prefix;	
	
}
