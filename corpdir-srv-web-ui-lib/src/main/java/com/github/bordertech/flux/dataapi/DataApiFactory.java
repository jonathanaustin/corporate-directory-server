package com.github.bordertech.flux.dataapi;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.wcomponents.util.Factory;

/**
 * Model provider factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataApiFactory {

	private static final DataApiProvider PROVIDER = Factory.newInstance(DataApiProvider.class);

	private DataApiFactory() {
	}

	public static <T extends DataApi> T getInstance(final String type) {
		return (T) PROVIDER.getDataApi(type);
	}

}
