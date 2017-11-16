package com.github.bordertech.flux.app.dataapi.retrieve;

import com.github.bordertech.taskmanager.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <K> the key type
 * @param <T> the item type
 */
public interface RetrieveItemApi<K, T> extends RetrieveApi {

	T retrieve(final K key) throws ServiceException;

}
