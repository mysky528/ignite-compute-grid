package com.rdquest.ignite.ml.api.exceptions;

public class IgniteMLException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7137577979409546839L;

	public IgniteMLException(String message) {
        super(message);
    }
	
	/**
	 * Rethrower
	 * @param message
	 * @param throwable
	 */
	public IgniteMLException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
