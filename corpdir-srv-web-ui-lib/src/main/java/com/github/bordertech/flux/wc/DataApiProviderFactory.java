package com.github.bordertech.flux.wc;

import com.github.bordertech.flux.DataApiProvider;

/**
 * Model provider factory.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DataApiProviderFactory {

	private static final DataApiProvider PROVIDER = new DefaultDataApiProvider();

	private DataApiProviderFactory() {
	}

	public static DataApiProvider getInstance() {
		return PROVIDER;
	}

}
