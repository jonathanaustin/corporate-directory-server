package com.github.bordertech.flux.app.dataapi.modify;

import com.github.bordertech.taskmanager.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <T> the entity type
 */
public interface ModifyEntityApi<T> extends ModifyApi {

	T create(final T entity) throws ServiceException;

	T update(final T entity) throws ServiceException;

	void delete(final T entity) throws ServiceException;

	T createInstance();

}
