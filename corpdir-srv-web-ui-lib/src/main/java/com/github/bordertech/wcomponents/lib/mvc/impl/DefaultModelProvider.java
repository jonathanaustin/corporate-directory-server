package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.lib.mvc.Model;
import com.github.bordertech.wcomponents.lib.mvc.ModelProvider;
import com.github.bordertech.wcomponents.util.Config;
import java.util.HashMap;
import java.util.Map;

/**
 * Default model provider.
 * <p>
 * Will look for the implementation of the model with parameter "mvc.model.impl." + key.
 * </p>
 *
 * @author jonathan
 */
public class DefaultModelProvider implements ModelProvider {

	private static final Map<String, Model> MODELS = new HashMap<>();

	private static final String MODEL_KEY_PREFIX = "mvc.model.impl.";

	@Override
	public Model getModel(final String key) {
		Model model = MODELS.get(key);
		if (model != null) {
			return model;
		}
		// Get class name
		String param = MODEL_KEY_PREFIX + key;
		String className = Config.getInstance().getString(param);
		if (className == null) {
			throw new IllegalArgumentException("Cannot find model parameter [" + param + "].");
		}
		try {
			model = (Model) Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Could not create model instance [" + className + "] for key [" + param + "]");
		}
		MODELS.put(key, model);
		return model;
	}

}
