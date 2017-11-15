package com.github.bordertech.flux.app.dataapi.modify;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.wcomponents.task.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ModifyApi<T> extends DataApi {

	T create(final T entity) throws ServiceException;

	T update(final T entity) throws ServiceException;

	void delete(final T entity) throws ServiceException;

	T createInstance();

}
