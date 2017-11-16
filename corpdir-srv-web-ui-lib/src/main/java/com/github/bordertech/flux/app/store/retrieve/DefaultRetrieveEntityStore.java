package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.retrieve.RetrieveEntityApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.flux.dataapi.DataApiType;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceException;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jonathan
 */
public class DefaultRetrieveEntityStore<K, T, S, R extends RetrieveEntityApi<K, T, S>> extends DefaultRetrieveItemStore<K, T, R> implements RetrieveEntityStore<K, T, S> {

	public DefaultRetrieveEntityStore(final DataApiType apiType, final StoreType storeType) {
		super(apiType, storeType);
	}

	public DefaultRetrieveEntityStore(final DataApiType apiType, final StoreType storeType, final String qualifier) {
		super(apiType, storeType, qualifier);
	}

	@Override
	public ServiceStatus getSearchStatus(final S criteria) {
		return getEventStatus(RetrieveBaseEventType.SEARCH, criteria);
	}

	@Override
	public boolean isSearchDone(S criteria) {
		return isEventDone(RetrieveBaseEventType.SEARCH, criteria);
	}

	@Override
	public List<T> search(S criteria) throws ServiceException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected ServiceAction<?, ?> getWrappedApiServiceAction(final RetrieveEventType type) {
		if (Objects.equals(type, RetrieveBaseEventType.SEARCH)) {
			return new ServiceAction<S, List<T>>() {
				@Override
				public List<T> service(final S item) throws ServiceException {
					return getRetrieveApi().search(item);
				}
			};
		}
		return super.getWrappedApiServiceAction(type);
	}

}
