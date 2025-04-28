package com.lmlasmo.shrul.infra.exception;

public class UnchangedFieldException extends RuntimeException {

	private static final long serialVersionUID = -4661879435243751491L;

	public UnchangedFieldException(String message) {
		super(message);
	}

}
