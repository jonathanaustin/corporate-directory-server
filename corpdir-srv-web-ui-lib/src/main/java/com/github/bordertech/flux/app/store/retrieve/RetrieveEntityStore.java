package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.taskmanager.service.ServiceStatus;

/**
 * Store that holds entity values.
 *
 * @author jonathan
 * @param <S> the search criteria type
 * @param <K> the entity key type
 * @param <T> the entity type
 */
public interface RetrieveEntityStore<S, T, K> extends RetrieveSearchStore<S, T> {

	T retrieve(final K key);

	ServiceStatus getRetrieveStatus(final K key);

	boolean isRetrieveDone(final K key);
}
