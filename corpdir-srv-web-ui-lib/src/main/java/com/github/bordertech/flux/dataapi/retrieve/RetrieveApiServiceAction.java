package com.github.bordertech.flux.dataapi.retrieve;

import com.github.bordertech.flux.dataapi.DataApiFactory;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.wcomponents.task.service.ServiceAction;
import com.github.bordertech.wcomponents.task.service.ServiceException;

/**
 *
 * @author jonathan
 */
public class RetrieveApiServiceAction<S, T> implements ServiceAction<S, T> {

	private final RetrieveApi<S, T> api;

	public RetrieveApiServiceAction(final DataApiType type) {
		api = (RetrieveApi<S, T>) DataApiFactory.getInstance(type);
	}

	@Override
	public T service(final S key) throws ServiceException {
		try {
			return api.retrieve(key);
		} catch (Exception e) {
			throw new ServiceException("Error retrieving data. " + e.getMessage(), e);
		}
	}

}
