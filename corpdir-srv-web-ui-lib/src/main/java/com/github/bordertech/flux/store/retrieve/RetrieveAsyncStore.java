package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.wcomponents.task.service.ServiceStatus;

/**
 * Store that loads its state async.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveAsyncStore<S, T> extends RetrieveStore<S, T> {

	ServiceStatus getStatus(final S criteria);

	boolean isDone(final S criteria);

}
