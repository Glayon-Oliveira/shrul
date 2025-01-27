package com.lmlasmo.shrul.infra.erro;

public class EmailConfirmationException extends RuntimeException{
	
	private static final long serialVersionUID = -3223380533906120383L;

	public EmailConfirmationException() {
		super();
	}
	
	public EmailConfirmationException(String message) {
		super(message);
	}

}
