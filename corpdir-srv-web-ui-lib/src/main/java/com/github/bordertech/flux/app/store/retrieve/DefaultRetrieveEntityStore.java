package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

/**
 * @param <T> the item type
 * @param <D> the API data type
 *
 * @author jonathan
 */
public class DefaultRetrieveEntityStore<T, D extends CrudApi<T>> extends AbstractRetrieveDataApiStore<D> implements RetrieveEntityStore<T> {

	public DefaultRetrieveEntityStore(final D api, final StoreType storeType) {
		super(api, storeType);
	}

	public DefaultRetrieveEntityStore(final D api, final StoreType storeType, final String qualifier) {
		super(api, storeType, qualifier);
	}

	@Override
	public ServiceStatus getRetrieveStatus(final T key) {
		return getEventStatus(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	public boolean isRetrieveDone(final T key) {
		return isEventDone(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	public T retrieve(final T key) {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getEventResult(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveEventType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseEventType.SEARCH)) {
			return getDataApi().refresh((T) criteria);
		}
		throw new IllegalStateException("Event not supported [" + type + "].");
	}

}
