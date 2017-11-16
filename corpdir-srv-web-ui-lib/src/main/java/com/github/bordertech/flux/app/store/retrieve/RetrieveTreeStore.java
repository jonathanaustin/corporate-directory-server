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
public interface RetrieveTreeStore<S, T, U> extends RetrieveEntityStore<S, T, U>, RetrieveTreeApi<S, T, U> {

	ServiceStatus getChildrenStatus(final T item);

	boolean isChildrenDone(final T item);

	ServiceStatus getRootItemsStatus();

	boolean isRootItemsDone();

}
