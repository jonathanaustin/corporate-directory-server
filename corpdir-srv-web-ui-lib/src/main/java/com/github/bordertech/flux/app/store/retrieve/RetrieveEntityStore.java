package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveEntityApi;
import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds entity values.
 *
 * @author jonathan
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <S> the search criteria type
 */
public interface RetrieveEntityStore<K, T, S> extends RetrieveItemStore<K, T>, RetrieveEntityApi<K, T, S> {

	ServiceStatus getSearchStatus(final S criteria);

	boolean isSearchDone(final S criteria);
}
