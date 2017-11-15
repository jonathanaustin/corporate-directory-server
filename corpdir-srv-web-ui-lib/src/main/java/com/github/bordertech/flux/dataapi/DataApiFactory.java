package com.github.bordertech.flux.dataapi;

import com.github.bordertech.flux.DataApi;

/**
 * Model provider factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataApiFactory {

	private static final DataApiProvider PROVIDER = new DefaultDataApiProvider();

	private DataApiFactory() {
	}

	public static <T extends DataApi> T getInstance(final DataApiType type) {
		return (T) PROVIDER.getDataApi(type);
	}

}
