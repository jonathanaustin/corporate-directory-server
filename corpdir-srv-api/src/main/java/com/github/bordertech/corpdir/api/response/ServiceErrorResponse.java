package com.github.bordertech.corpdir.api.response;

/**
 * Service error response with meta data and error details. `
 *
 * @author jonathan
 *
 */
public class ServiceErrorResponse extends ServiceBasicResponse {

	private ErrorDetail error;

	public ServiceErrorResponse(final ErrorDetail data) {
		this.error = data;
	}

	public ErrorDetail getError() {
		return error;
	}

	public void setError(final ErrorDetail error) {
		this.error = error;
	}

}
