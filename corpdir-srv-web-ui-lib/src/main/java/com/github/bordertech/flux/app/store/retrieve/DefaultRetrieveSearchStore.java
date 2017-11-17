package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.SearchApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.List;
import java.util.Objects;

/**
 * Default search store.
 *
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <D> the data API type
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultRetrieveSearchStore<S, T, D extends SearchApi<S, T>> extends AbstractRetrieveDataApiStore<D> implements RetrieveSearchStore<S, T> {

	public DefaultRetrieveSearchStore(final D api, final StoreType storeType) {
		super(api, storeType);
	}

	public DefaultRetrieveSearchStore(final D api, final StoreType storeType, final String qualifier) {
		super(api, storeType, qualifier);
	}

	@Override
	public ServiceStatus getSearchStatus(final S criteria) {
		return getEventStatus(RetrieveBaseEventType.SEARCH, criteria);
	}

	@Override
	public boolean isSearchDone(final S criteria) {
		return isEventDone(RetrieveBaseEventType.SEARCH, criteria);
	}

	@Override
	public List<T> search(final S criteria) {
		return (List<T>) getEventResult(RetrieveBaseEventType.SEARCH, criteria);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveEventType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseEventType.RETRIEVE)) {
			return getDataApi().search((S) criteria);
		}
		throw new IllegalStateException("Event not supported [" + type + "].");
	}

}
