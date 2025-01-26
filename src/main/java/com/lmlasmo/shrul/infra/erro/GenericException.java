package com.lmlasmo.shrul.infra.erro;

public class GenericException extends RuntimeException{

	private static final long serialVersionUID = 422975543272298397L;
	
	public GenericException() {
		super();
	}
	
	public GenericException(String message) {
		super(message);
	}

}
