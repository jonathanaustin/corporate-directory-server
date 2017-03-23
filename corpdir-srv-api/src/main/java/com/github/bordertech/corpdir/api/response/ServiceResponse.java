package com.github.bordertech.corpdir.api.response;

/**
 * Service response with meta data, data and error. `
 *
 * @author jonathan
 * @param <T> the data type in the response
 *
 */
public class ServiceResponse<T> extends ServiceBasicResponse {

	private T data;

	public ServiceResponse(final T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(final T data) {
		this.data = data;
	}

}
