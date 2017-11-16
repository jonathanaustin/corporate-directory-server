package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveItemApi;
import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <K> the item key type
 * @param <T> the item type
 */
public interface RetrieveItemStore<K, T> extends Store, RetrieveItemApi<K, T> {

	ServiceStatus getRetrieveStatus(final K key);

	boolean isRetrieveDone(final K key);

}
