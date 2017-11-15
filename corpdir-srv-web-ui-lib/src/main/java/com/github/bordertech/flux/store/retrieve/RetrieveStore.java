package com.github.bordertech.flux.store.retrieve;

import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.dataapi.retrieve.RetrieveApi;
import com.github.bordertech.wcomponents.task.service.ServiceStatus;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the result type
 */
public interface RetrieveStore<S, T> extends Store, RetrieveApi<S, T> {

	ServiceStatus getRetrieveStatus(final S criteria);

	boolean isRetrieveDone(final S criteria);

}
