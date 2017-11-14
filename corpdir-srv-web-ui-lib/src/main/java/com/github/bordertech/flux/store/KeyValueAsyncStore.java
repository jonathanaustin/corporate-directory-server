package com.github.bordertech.flux.store;

import com.github.bordertech.wcomponents.task.service.ServiceStatus;

/**
 * Store that loads its state async.
 *
 * @author jonathan
 * @param <K> the key type
 * @param <V> the value type
 */
public interface KeyValueAsyncStore<K, V> extends KeyValueStore<K, V> {

	ServiceStatus getStatus(final K key) throws StoreException;

}
