package com.github.bordertech.flux.crud.factory;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import com.github.bordertech.flux.crud.dataapi.SearchApi;
import com.github.bordertech.flux.crud.store.retrieve.DefaultEntityStore;
import com.github.bordertech.flux.crud.store.retrieve.DefaultEntityTreeStore;
import com.github.bordertech.flux.crud.store.retrieve.DefaultSearchStore;

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

	public static <T extends Store> T getInstance(final String storeKey, final String apiKey) {
		try {
			DataApi api = DataApiFactory.getInstance(apiKey);
			if (api instanceof CrudTreeApi && storeKey.startsWith("e-")) {
				return (T) new DefaultEntityTreeStore(storeKey, (CrudTreeApi) api);
			} else if (api instanceof CrudApi && storeKey.startsWith("e-")) {
				return (T) new DefaultEntityStore(storeKey, (CrudApi) api);
			} else if (api instanceof SearchApi && storeKey.startsWith("s-")) {
				return (T) new DefaultSearchStore(storeKey, (SearchApi) api);
			} else {
				throw new IllegalStateException("Default Stores do not support Data API [" + storeKey + "].");
			}
		} catch (Exception e) {
			throw new IllegalStateException("Could not create store [" + storeKey + "]." + e.getMessage(), e);
		}
	}

}
