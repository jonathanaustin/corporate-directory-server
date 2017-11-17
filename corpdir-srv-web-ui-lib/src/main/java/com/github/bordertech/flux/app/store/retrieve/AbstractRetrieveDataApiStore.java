package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.StoreType;

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

	public AbstractRetrieveDataApiStore(final D api, final StoreType storeType) {
		this(api, storeType, null);
	}

	public AbstractRetrieveDataApiStore(final D api, final StoreType storeType, final String qualifier) {
		super(storeType, qualifier);
		this.api = api;
	}

	protected D getDataApi() {
		return api;
	}

}
