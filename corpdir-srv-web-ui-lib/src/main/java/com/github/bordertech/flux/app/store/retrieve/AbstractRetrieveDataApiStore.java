package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.key.StoreKey;

/**
 * Abstract search store that uses the data api interface.
 *
 * @param <D> the data API type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractRetrieveDataApiStore<D extends DataApi> extends AbstractRetrieveStore {

	private final D api;

	public AbstractRetrieveDataApiStore(final StoreKey storeKey, final D api) {
		super(storeKey);
		this.api = api;
	}

	protected D getDataApi() {
		return api;
	}

}
