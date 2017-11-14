package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <K> the key type
 * @param <V> the value type
 */
public interface KeyValueStore<K, V> extends Store {

	V getValue(final K key) throws StoreException;

}
