package com.github.bordertech.corpdir.api.exception;

/**
 * Service exception that can be thrown if error handling request.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class ServiceApiException extends RuntimeException {

	private final int code;

	/**
	 * @param message the exception message
	 */
	public ServiceApiException(final String message) {
		this(message, -1);
	}

	/**
	 * @param message the exception message
	 * @param original the original exception
	 */
	public ServiceApiException(final String message, final Throwable original) {
		this(message, original, -1);
	}

	/**
	 * @param message the exception message
	 * @param code the status code
	 */
	public ServiceApiException(final String message, final int code) {
		super(message);
		this.code = code;
	}

	/**
	 * @param message the exception message
	 * @param original the original exception
	 * @param code the status code
	 */
	public ServiceApiException(final String message, final Throwable original, final int code) {
		super(message, original);
		this.code = code;
	}

	/**
	 * @return the status code
	 */
	public int getCode() {
		return code;
	}

}
