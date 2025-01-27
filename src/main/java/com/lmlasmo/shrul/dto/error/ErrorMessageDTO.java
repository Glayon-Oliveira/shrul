package com.lmlasmo.shrul.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessageDTO {

	@JsonProperty
	@JsonInclude(value = Include.NON_NULL)
	private String cause;

	@JsonProperty
	private String message;

	public ErrorMessageDTO(String message) {
		this.message = message;
	}

	public ErrorMessageDTO(String cause, String message) {
		this.cause = cause;
		this.message = message;
	}

	public ErrorMessageDTO(Throwable cause, String message) {
		this.message = message;
		if(cause != null) {
			this.cause = cause.getMessage();
		}
	}

	public ErrorMessageDTO(Exception exception) {
		this(exception.getCause(), exception.getMessage());
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
