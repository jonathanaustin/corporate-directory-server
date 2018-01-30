package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;
import com.github.bordertech.taskmanager.service.CallType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import java.util.List;

/**
 * Store that performs a search.
 *
 * @param <S> the search criteria
 * @param <K> the item key type
 * @param <T> the item type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SearchStore<S, K, T> extends Store {

	ResultHolder<S, List<T>> search(final S criteria, final CallType callType);

	K getItemKey(final T item);

}
