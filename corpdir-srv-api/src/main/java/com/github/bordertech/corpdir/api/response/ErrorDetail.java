package com.github.bordertech.corpdir.api.response;

import java.io.Serializable;

/**
 * Response error details.
 *
 * @author jonathan
 */
public class ErrorDetail implements Serializable {

	private final int status;
	private final String message;
	private final String moreInfoUrl;

	/**
	 * @param status error code
	 * @param message error message
	 */
	public ErrorDetail(final int status, final String message) {
		this(status, message, null);
	}

	/**
	 * @param status status code
	 * @param message error message
	 * @param moreInfoUrl link to more info
	 */
	public ErrorDetail(final int status, final String message, final String moreInfoUrl) {
		this.status = status;
		this.message = message;
		this.moreInfoUrl = moreInfoUrl;
	}

	/**
	 * @return the status code
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return the error message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the link to more info
	 */
	public String getMoreInfoUrl() {
		return moreInfoUrl;
	}

}
