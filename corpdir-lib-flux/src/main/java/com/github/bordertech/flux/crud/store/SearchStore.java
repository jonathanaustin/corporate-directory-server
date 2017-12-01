package com.github.bordertech.flux.crud.store;

import java.util.List;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <S> the search criteria
 * @param <T> the item type
 */
public interface SearchStore<S, T> extends RetrieveActionStore {

	List<T> search(final S criteria);

	boolean isSearchDone(final S criteria);

}
