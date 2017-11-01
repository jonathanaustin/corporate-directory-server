package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.event.StateEventType;
import com.github.bordertech.flux.StoreKey;
import com.github.bordertech.flux.StoreType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractStore implements Store {

	private final StoreKey storeKey;
	private final Set<String> registeredIds = new HashSet<>();

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
		Dispatcher dispatcher = getDispatcher();
		for (String id : registeredIds) {
			dispatcher.unregisterListener(id);
		}
	}

	@Override
	public void dispatchChangeEvent() {
		DefaultEvent event = new DefaultEvent(new EventKey(StateEventType.STORE_CHANGED, storeKey.getQualifier()), this);
		getDispatcher().dispatch(event);
	}

	/**
	 * A helper method to register a listener with an Event Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 */
	protected void registerListener(final EventType eventType, final Listener listener) {
		String id = getDispatcher().registerListener(new EventKey(eventType, storeKey.getQualifier()), listener);
		registeredIds.add(id);
	}

}
