package com.github.bordertech.flux.crud.factory;

import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.crud.actioncreator.impl.DefaultEntityActionCreator;
import com.github.bordertech.flux.crud.actioncreator.impl.DefaultEntityTreeActionCreator;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import java.util.HashMap;
import java.util.Map;

/**
 * Default CRUD Action Creator.
 * <p>
 * At the moment ACTION Creators are singleton for the JVM.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultCrudActionCreatorFactory {

	private static final Map<String, ActionCreator> CREATORS = new HashMap<>();

	private DefaultCrudActionCreatorFactory() {
	}

	public static <T extends ActionCreator> T getInstance(final String key, final String apiKey) {
		ActionCreator creator = CREATORS.get(key);
		if (creator != null) {
			return (T) creator;
		}

		try {
			DataApi api = DataApiFactory.getInstance(apiKey);
			if (api instanceof CrudTreeApi) {
				creator = new DefaultEntityTreeActionCreator<>(key, (CrudTreeApi) api);
			} else {
				creator = new DefaultEntityActionCreator<>(key, (CrudApi) api);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Could not create action creator [" + key + "]." + e.getMessage(), e);
		}
		CREATORS.put(key, creator);
		return (T) creator;
	}

}
