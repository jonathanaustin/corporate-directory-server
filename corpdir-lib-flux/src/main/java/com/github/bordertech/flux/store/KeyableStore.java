package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;

/**
 * Store that has an item with a key.
 *
 * @param <K> the item key type
 * @param <T> the item type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface KeyableStore<K, T> extends Store {

	K getItemKey(final T item);

}
