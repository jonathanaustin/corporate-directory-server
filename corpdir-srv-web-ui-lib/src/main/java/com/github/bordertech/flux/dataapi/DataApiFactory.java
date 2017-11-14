package com.github.bordertech.flux.dataapi;

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

	public static <T extends DataApi> DataApi getInstance(final DataApiType type) {
		return PROVIDER.getDataApi(type);
	}

}
