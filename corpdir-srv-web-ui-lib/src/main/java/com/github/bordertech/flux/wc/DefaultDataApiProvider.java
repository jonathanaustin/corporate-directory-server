package com.github.bordertech.flux.wc;

import com.github.bordertech.wcomponents.util.Config;
import java.util.HashMap;
import java.util.Map;

/**
 * Default data api provider.
 * <p>
 * Will look for the implementation of the data api with parameter "wc.data.api.impl." + key.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiProvider implements DataApiProvider {

	private static final Map<String, DataApi> DATA_APIS = new HashMap<>();

	private static final String DATA_API_KEY_PREFIX = "wc.data.api.impl.";

	@Override
	public DataApi getDataApi(final String key) {
		DataApi model = DATA_APIS.get(key);
		if (model != null) {
			return model;
		}
		// Get class name
		String param = DATA_API_KEY_PREFIX + key;
		String className = Config.getInstance().getString(param);
		if (className == null) {
			throw new IllegalArgumentException("Cannot find data api parameter [" + param + "].");
		}
		try {
			model = (DataApi) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Could not create data api instance [" + className + "] for key [" + param + "]");
		}
		DATA_APIS.put(key, model);
		return model;
	}

}
