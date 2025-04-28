package com.lmlasmo.shrul.dto.update;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinkUpdateDTO {

	@JsonProperty
	@NotBlank
	@Size(min = 10)
	private String id;

	@JsonProperty
	@NotNull
	@Min(1)
	private BigInteger prefixId;

}
