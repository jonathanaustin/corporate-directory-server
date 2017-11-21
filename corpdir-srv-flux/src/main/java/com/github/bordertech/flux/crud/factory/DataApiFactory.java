package com.github.bordertech.flux.crud.factory;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.locator.BindingFactory;

/**
 * Model provider factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataApiFactory {

	private static final DataApiProvider PROVIDER = BindingFactory.newInstance(DataApiProvider.class);

	private DataApiFactory() {
	}

	public static <T extends DataApi> T getInstance(final String type) {
		return (T) PROVIDER.getDataApi(type);
	}

}
