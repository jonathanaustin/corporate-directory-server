package com.github.bordertech.corpdir.api.response;

/**
 * Service response with meta data, data and error. `
 *
 * @author jonathan
 * @param <T> the data type in the response
 *
 */
public class DataResponse<T> extends BasicResponse {

	private T data;

	public DataResponse(final T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(final T data) {
		this.data = data;
	}

}
