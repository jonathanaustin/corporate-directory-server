package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveTreeApi;
import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds tree values.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public interface RetrieveTreeStore<S, T> extends RetrieveListStore<S, T>, RetrieveTreeApi<S, T> {

	ServiceStatus getRetrieveChildrenStatus(final T item);

	boolean isRetrieveChildrenDone(final T item);

	ServiceStatus getRetrieveRootStatus();

	boolean isRetrieveRootDone();

}
