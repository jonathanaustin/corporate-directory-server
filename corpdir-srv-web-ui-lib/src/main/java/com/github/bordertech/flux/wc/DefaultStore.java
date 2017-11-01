package com.github.bordertech.flux.wc;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.impl.AbstractStore;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultStore extends AbstractStore {

	public DefaultStore(final StoreType type) {
		this(type, null);
	}

	public DefaultStore(final StoreType type, final String qualifier) {
		super(type, qualifier);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	/**
	 * Helper method to load a data API.
	 *
	 * @param key
	 * @return
	 */
	protected DataApi getDataApi(final String key) {
		DataApi model = DataApiProviderFactory.getInstance().getDataApi(key);
		return model;
	}

}
