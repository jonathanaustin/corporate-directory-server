package com.github.bordertech.flux.dataapi;

import com.github.bordertech.flux.DataApi;
import java.util.List;

/**
 * Search API.
 *
 * @param <S> the search criteria type
 * @param <T> the entity type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface SearchApi<S, K, T> extends DataApi {

	List<T> search(final S criteria);

	K getItemKey(final T item);

}
