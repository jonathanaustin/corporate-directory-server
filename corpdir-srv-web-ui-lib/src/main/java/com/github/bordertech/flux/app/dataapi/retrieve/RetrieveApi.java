package com.github.bordertech.flux.app.dataapi.retrieve;

import com.github.bordertech.flux.DataApi;
import com.github.bordertech.taskmanager.service.ServiceException;

/**
 *
 * @author jonathan
 * @param <S> the criteria type
 * @param <T> the response type
 */
public interface RetrieveApi<S, T> extends DataApi {

	T retrieve(final S criteria) throws ServiceException;

}
