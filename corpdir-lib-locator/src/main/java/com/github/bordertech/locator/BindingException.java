package com.github.bordertech.locator;

/**
 * Binding Exception.
 *
 * @author jonathan
 */
public class BindingException extends RuntimeException {

	/**
	 * Creates a FactoryException with the specified message.
	 *
	 * @param msg the message.
	 */
	public BindingException(final String msg) {
		super(msg);
	}

	/**
	 * Creates a FactoryException with the specified message and cause.
	 *
	 * @param msg the message.
	 * @param throwable the cause of the exception.
	 */
	public BindingException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

	/**
	 * Creates a FactoryException with the specified cause.
	 *
	 * @param throwable the cause of the exception.
	 */
	public BindingException(final Throwable throwable) {
		super(throwable);
	}
}
