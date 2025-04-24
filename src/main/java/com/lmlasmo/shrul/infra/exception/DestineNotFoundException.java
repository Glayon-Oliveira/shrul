package com.lmlasmo.shrul.infra.exception;

public class DestineNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2293236500685908012L;

	public DestineNotFoundException() {
		super();
	}

	public DestineNotFoundException(String message) {
		super(message);
	}

}
