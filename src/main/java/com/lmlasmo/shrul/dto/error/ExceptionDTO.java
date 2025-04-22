package com.lmlasmo.shrul.dto.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ExceptionDTO {

	@JsonProperty
	private LocalDateTime timestamp = LocalDateTime.now();

	@JsonProperty
	@NonNull
	private Integer status;

	@JsonProperty
	@NonNull
	private String message;
	
	@JsonProperty
	@JsonInclude(value = Include.NON_NULL)
	@NonNull
	private ErrorMessageDTO error;
	
	@JsonProperty
	@JsonInclude(value = Include.NON_NULL)	
	private List<ErrorMessageDTO> errors;
	
	public ExceptionDTO(Integer status, String message, ErrorMessageDTO... erros) {
		this.status = status;
		this.message = message;
		if(erros.length == 0) {
			this.error = erros[0];
		}else {
			this.errors = List.of(erros);
		}		
	}

}
