package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.flux.DataApi;
import java.util.Set;

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

	public AbstractRetrieveDataApiStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys);
		this.api = api;
	}

	protected D getDataApi() {
		return api;
	}

}
