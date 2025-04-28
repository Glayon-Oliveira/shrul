package com.lmlasmo.shrul.infra.exception;

public class DestinationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2293236500685908012L;

	public DestinationNotFoundException() {
		super();
	}

	public DestinationNotFoundException(String message) {
		super(message);
	}

}
