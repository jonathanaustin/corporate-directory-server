package com.github.bordertech.corpdir.api.response;

/**
 * Service error response with meta data and error details. `
 *
 * @author jonathan
 *
 */
public class ErrorResponse extends BasicResponse {

	private ErrorDetail error;

	public ErrorResponse(final ErrorDetail data) {
		this.error = data;
	}

	public ErrorDetail getError() {
		return error;
	}

	public void setError(final ErrorDetail error) {
		this.error = error;
	}

}
