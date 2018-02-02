package com.github.bordertech.flux.dataapi;

import com.github.bordertech.flux.DataApi;

/**
 * Data API Items have a key.
 *
 * @param <K> the entity key type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface KeyableApi<K, T> extends DataApi {

	K getItemKey(final T item);

}
