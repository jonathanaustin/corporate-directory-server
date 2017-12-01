package com.github.bordertech.flux.store.collection;

import com.github.bordertech.flux.Store;
import java.util.Map;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <K> the key type
 * @param <V> the value type
 */
public interface MapStore<K, V> extends Store {

	V getValue(final K key);

	Map<K, V> getMap();

}
