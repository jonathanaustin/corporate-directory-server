package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds entity values.
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface RetrieveEntityStore<T> extends RetrieveStore {

	T retrieve(final T entity);

	ServiceStatus getRetrieveStatus(final T entity);

	boolean isRetrieveDone(final T entity);
}
