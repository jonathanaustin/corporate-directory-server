package com.github.bordertech.flux;

import com.github.bordertech.flux.key.EventKey;
import com.github.bordertech.flux.key.StoreKey;
import java.io.Serializable;

/**
 * Event dispatcher.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface Dispatcher extends Serializable {

	void dispatch(final Event event);

	String registerListener(final EventKey matcher, final Listener listener);

	void unregisterListener(final String registerId);

	boolean isDispatching();

	Listener getListener(final String registerId);

	void registerStore(final Store store);

	void unregisterStore(final StoreKey storeKey);

	Store getStore(final StoreKey storeKey);

}
