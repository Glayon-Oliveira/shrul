package com.lmlasmo.shrul.infra.exception;

public class InvalidPasswordException extends RuntimeException {

	private static final long serialVersionUID = 4284922845567462411L;
	
	public InvalidPasswordException(String message) {
		super(message);
	}

}
