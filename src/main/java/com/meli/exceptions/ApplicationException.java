package com.meli.exceptions;

public abstract class ApplicationException extends Exception{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	protected ApplicationException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
