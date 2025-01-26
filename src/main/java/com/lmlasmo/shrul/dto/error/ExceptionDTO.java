package com.lmlasmo.shrul.dto.error;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExceptionDTO {
	
	@JsonProperty
	private LocalDateTime timestamp = LocalDateTime.now();
	
	@JsonProperty
	private int status;
		
	private String message;
	private ErrorMessageDTO error;	
	private List<ErrorMessageDTO> errors;
	
	public ExceptionDTO(int status, ErrorMessageDTO... errors) {
		
		this.status = status;				
		
		setErrors(errors);
	}
	
	public ExceptionDTO(HttpStatus status, ErrorMessageDTO... errors) {
		this(status.value(), errors);
	}
	
	public ExceptionDTO(int status, Exception... errors) {
		this.status = status;
		
		setErrors(errors);
	}
	
	public ExceptionDTO(HttpStatus status, Exception... errors) {
		this(status.value(), errors);
	}
	
	private void setErrors(ErrorMessageDTO... errors) {
		
		if(errors.length == 1) {
			
			if(errors[0].getCause() == null) {
				this.message = errors[0].getMessage();
			}else {
				error = errors[0];
			}
						
			return;
		}
		
		this.errors = List.of(errors);
	}
	
	private void setErrors(Exception... errors) {
		
		if(errors.length == 1) {
			
			if(errors[0].getCause() == null) {
				this.message = errors[0].getMessage();
			}else {
				error = new ErrorMessageDTO(errors[0]);
			}
						
			return;
		}
		
		this.errors = List.of(errors).stream().map(e -> new ErrorMessageDTO(e)).toList();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}	

	public int getStatus() {
		return status;
	}	

	@JsonProperty("error")
	public Object getErrors() {
		
		if(message != null) {
			return message;
		}
		
		return (errors != null) ? errors : error;
	}	
	
}
