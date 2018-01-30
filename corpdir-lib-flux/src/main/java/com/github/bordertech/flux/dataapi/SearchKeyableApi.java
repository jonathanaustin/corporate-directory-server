package com.github.bordertech.flux.dataapi;

/**
 * Search API with Keyable Item.
 *
 * @param <S> the search criteria type
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SearchKeyableApi<S, K, T> extends SearchApi<S, T>, KeyableApi<K, T> {

}
