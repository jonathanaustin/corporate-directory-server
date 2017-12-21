package com.github.bordertech.flux.crud.store;

/**
 * Exception occurred while performing a retrieve action.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class RetrieveActionException extends Exception {

	/**
	 * @param message the exception message
	 */
	public RetrieveActionException(final String message) {
		super(message);
	}

	/**
	 * @param message the exception message
	 * @param original the original exception
	 */
	public RetrieveActionException(final String message, final Throwable original) {
		super(message, original);
	}

}
