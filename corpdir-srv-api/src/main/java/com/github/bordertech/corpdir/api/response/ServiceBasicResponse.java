package com.github.bordertech.corpdir.api.response;

import java.io.Serializable;

/**
 * Service response with meta data and error. `
 *
 * @author jonathan
 *
 */
public class ServiceBasicResponse implements Serializable {

	private MetaData meta;

	public MetaData getMeta() {
		return meta;
	}

	public void setMeta(final MetaData meta) {
		this.meta = meta;
	}
}
