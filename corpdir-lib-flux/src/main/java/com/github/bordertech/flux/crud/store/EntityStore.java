package com.github.bordertech.flux.crud.store;

import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds entity values.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface EntityStore<T> extends RetrieveActionStore {

	T fetch(final T entity);

	ServiceStatus getFetchStatus(final T entity);

	boolean isFetchDone(final T entity);
}
