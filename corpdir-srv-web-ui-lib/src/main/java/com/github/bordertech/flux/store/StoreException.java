package com.github.bordertech.flux.store;

/**
 * Exception processing polling action.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class StoreException extends Exception {

	/**
	 * @param msg exception message
	 */
	public StoreException(final String msg) {
		super(msg);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public StoreException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

}
