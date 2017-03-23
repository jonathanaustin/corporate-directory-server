package com.github.bordertech.corpdir.api.response;

import java.io.Serializable;

/**
 * Service response with meta data and error. `
 *
 * @author jonathan
 *
 */
public class ServiceBasicResponse implements Serializable {

	private ResponseMetaData metaData;
	private ResponseError responseError;

	public ResponseMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(final ResponseMetaData metaData) {
		this.metaData = metaData;
	}

	public ResponseError getResponseError() {
		return responseError;
	}

	public void setResponseError(final ResponseError responseError) {
		this.responseError = responseError;
	}

}
