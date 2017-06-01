package com.github.bordertech.corpdir.web.ui.polling;

/**
 * Exception processing polling service.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingServiceException extends Exception {

	/**
	 * @param msg exception message
	 */
	public PollingServiceException(final String msg) {
		super(msg);
	}

	/**
	 * @param msg exception message
	 * @param throwable original exception
	 */
	public PollingServiceException(final String msg, final Throwable throwable) {
		super(msg, throwable);
	}

}
