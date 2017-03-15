package com.github.bordertech.corpdir.api.exception;

/**
 * Not found exception.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class NotFoundException extends RuntimeException {

	/**
	 * @param message the exception message
	 */
	public NotFoundException(final String message) {
		super(message);
	}

}
