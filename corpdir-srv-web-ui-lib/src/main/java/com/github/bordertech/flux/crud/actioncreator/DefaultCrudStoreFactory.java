package com.github.bordertech.flux.crud.actioncreator;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import com.github.bordertech.flux.crud.dataapi.SearchApi;
import com.github.bordertech.flux.crud.store.retrieve.DefaultEntityStore;
import com.github.bordertech.flux.crud.store.retrieve.DefaultEntityTreeStore;
import com.github.bordertech.flux.crud.store.retrieve.DefaultSearchStore;
import com.github.bordertech.flux.dataapi.DataApiFactory;

/**
 * Default CRUD Store Factory.
 * <p>
 * At the moment STORE are not singleton for the JVM but are singleton for the user context.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultCrudStoreFactory {

	private DefaultCrudStoreFactory() {
	}

	public static <T extends Store> T getInstance(final String key, final String apiKey) {
		try {
			DataApi api = DataApiFactory.getInstance(key);
			if (api instanceof CrudTreeApi) {
				return (T) new DefaultEntityTreeStore(key, (CrudTreeApi) api);
			} else if (api instanceof CrudApi) {
				return (T) new DefaultEntityStore(key, (CrudApi) api);
			} else if (api instanceof SearchApi) {
				return (T) new DefaultSearchStore(key, (SearchApi) api);
			} else {
				throw new IllegalStateException("Default Stores do not support Data API [" + key + "].");
			}
		} catch (Exception e) {
			throw new IllegalStateException("Could not create store [" + key + "]." + e.getMessage(), e);
		}
	}

}
