package com.lmlasmo.shrul.infra.exception;

public class ActionFailedException extends RuntimeException {

	private static final long serialVersionUID = -2416126917434703556L;

	public ActionFailedException(String message) {
		super(message);
	}

}
