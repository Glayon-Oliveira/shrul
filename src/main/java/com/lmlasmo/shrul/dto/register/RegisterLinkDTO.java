package com.lmlasmo.shrul.dto.register;

import java.math.BigInteger;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterLinkDTO {	

	@JsonProperty
	@NotBlank
	@URL
	private String destine;

	@JsonProperty("prefix_id")
	@NotNull
	@Min(1)
	private BigInteger prefixId;

}
