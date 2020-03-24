package com.youway.mybiz.exception;

public class UserNotFoundException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
		super("Could not find book " + id);
	}
}
