package com.github.bordertech.flux.store.impl;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.store.DataApiStore;
import java.util.Set;

/**
 * Default DATA API Store.
 *
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultDataApiStore<D extends DataApi> extends DefaultStore implements DataApiStore<D> {

	private final D api;

	public DefaultDataApiStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys);
		this.api = api;
	}

	@Override
	public D getDataApi() {
		return api;
	}

}
