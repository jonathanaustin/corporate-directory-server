package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.store.StoreException;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <S> the key type
 * @param <T> the value type
 */
public interface RetrieveStore<S, T> extends Store {

	T getValue(final S criteria) throws StoreException;

	ServiceStatus getStatus(final S criteria);

	boolean isDone(final S criteria);

}
