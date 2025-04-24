package com.lmlasmo.shrul.infra.exception;

public class SystemFailedException extends RuntimeException{

	private static final long serialVersionUID = -8395196930368959528L;
	
	public SystemFailedException(String message) {
		super(message);
	}

}
