package com.github.bordertech.flux.dataapi;

/**
 * Exception processing data api action.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataApiException extends Exception {

	/**
	 * @param msg exception message
	 */
	public DataApiException(final String msg) {
		super(msg);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public DataApiException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

}
