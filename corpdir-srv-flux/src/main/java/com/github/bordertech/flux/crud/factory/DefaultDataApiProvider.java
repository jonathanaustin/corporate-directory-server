package com.github.bordertech.flux.crud.factory;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.locator.BindingFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Default data api provider.
 * <p>
 * Will look for the implementation of the data api with parameter "wc.flux.dataapi.impl." + key.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiProvider implements DataApiProvider {

	private static final Map<String, DataApi> DATA_APIS = new HashMap<>();

	@Override
	public DataApi getDataApi(final String key) {
		DataApi model = DATA_APIS.get(key);
		if (model != null) {
			return model;
		}
		model = BindingFactory.newInstance("dataapi." + key);
		DATA_APIS.put(key, model);
		return model;
	}

}
