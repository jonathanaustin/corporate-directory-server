package com.github.bordertech.flux.dataapi.modify;

import com.github.bordertech.wcomponents.task.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <T> the data type
 */
public interface ModifyTreeApi<T> extends ModifyApi<T> {

	T addChild(final T parent, final T child) throws ServiceException;

	T removeChild(final T parent, final T child) throws ServiceException;

}
