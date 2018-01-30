package com.github.bordertech.flux.store;

/**
 * Store that performs a search and has a keyable item.
 *
 * @param <S> the search criteria
 * @param <K> the item key type
 * @param <T> the item type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SearchKeyableStore<S, K, T> extends SearchStore<S, T>, KeyableStore<K, T> {

}
