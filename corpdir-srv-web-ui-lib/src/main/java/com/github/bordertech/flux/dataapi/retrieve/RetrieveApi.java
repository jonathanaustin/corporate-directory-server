package com.github.bordertech.flux.dataapi.retrieve;

import com.github.bordertech.flux.dataapi.DataApi;
import com.github.bordertech.wcomponents.task.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveApi<S, T> extends DataApi {

	T retrieve(final S criteria) throws ServiceException;

}
