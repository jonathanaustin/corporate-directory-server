package com.github.bordertech.flux.dataapi.modify;

import com.github.bordertech.flux.dataapi.DataApi;
import com.github.bordertech.flux.dataapi.DataApiException;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ModifyApi<T> extends DataApi {

	T create(final T entity) throws DataApiException;

	T update(final T entity) throws DataApiException;

	void delete(final T entity) throws DataApiException;

	T createInstance();

}
