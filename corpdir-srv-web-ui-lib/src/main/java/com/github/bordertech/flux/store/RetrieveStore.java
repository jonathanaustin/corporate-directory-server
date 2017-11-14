package com.github.bordertech.flux.store;

import com.github.bordertech.flux.Store;

/**
 * Store that holds key value pairs.
 *
 * @author jonathan
 * @param <S> the key type
 * @param <T> the value type
 */
public interface RetrieveStore<S, T> extends Store {

	T getValue(final S criteria) throws StoreException;

}
