package com.github.bordertech.flux.wc;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.StoreChangeEventType;
import com.github.bordertech.flux.StoreKey;
import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.impl.DefaultEvent;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractStore implements Store {

	/**
	 * QUALIFIER pattern.
	 */
	private final StoreKey storeKey;
	private Set<String> registeredIds;

	public AbstractStore(final StoreType type) {
		this(type, null);
	}

	public AbstractStore(final StoreType type, final String qualifier) {
		this.storeKey = new StoreKey(type, qualifier);
	}

	@Override
	public StoreKey getStoreKey() {
		return storeKey;
	}

	@Override
	public Serializable getState() {
		return null;
	}

	@Override
	public void registerListeners() {
		// Do nothing
	}

	@Override
	public void unregisterListeners() {
		if (registeredIds != null) {
			Dispatcher dispatcher = getDispatcher();
			for (String id : registeredIds) {
				dispatcher.unregisterListener(id);
			}
			registeredIds = null;
		}
	}

	@Override
	public void dispatchChangeEvent() {
		DefaultEvent event = new DefaultEvent(new EventKey(new StoreChangeEventType(), storeKey.getQualifier()), this, null);
		getDispatcher().dispatch(event);
	}

	protected final Dispatcher getDispatcher() {
		return DispatcherFactory.getInstance();
	}

	/**
	 * A helper method to register a listener with an Event Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 */
	protected void registerListener(final EventType eventType, final Listener listener) {
		String id = getDispatcher().registerListener(new EventKey(eventType, storeKey.getQualifier()), listener);
		if (registeredIds == null) {
			registeredIds = new HashSet<>();
		}
		registeredIds.add(id);
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
