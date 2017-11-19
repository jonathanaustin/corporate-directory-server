package com.github.bordertech.flux.crud.dataapi;

import com.github.bordertech.flux.DataApi;
import java.util.List;

/**
 * Search API.
 *
 * @author jonathan
 * @param <S> the search criteria type
 * @param <T> the entity type
 */
public interface SearchApi<S, T> extends DataApi {

	List<T> search(final S criteria);

}
