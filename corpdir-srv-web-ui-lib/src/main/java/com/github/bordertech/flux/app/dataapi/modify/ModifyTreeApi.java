package com.github.bordertech.flux.app.dataapi.modify;

import com.github.bordertech.taskmanager.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ModifyTreeApi<T> extends ModifyEntityApi<T> {

	T addChild(final T parent, final T child) throws ServiceException;

	T removeChild(final T parent, final T child) throws ServiceException;

}
