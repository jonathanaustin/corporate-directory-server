package com.github.bordertech.flux.app.store.retrieve;

import com.github.bordertech.flux.StoreType;
import com.github.bordertech.flux.app.dataapi.CrudApi;
import com.github.bordertech.flux.app.event.RetrieveEventType;
import com.github.bordertech.flux.app.event.base.RetrieveBaseEventType;
import com.github.bordertech.taskmanager.service.ServiceStatus;
import java.util.Objects;

/**
 * @param <S> the key type
 * @param <T> the item type
 * @param <K> the item key type
 * @param <D> the data api type
 *
 * @author jonathan
 */
public class DefaultRetrieveEntityStore<S, T, K, D extends CrudApi<S, T, K>> extends DefaultRetrieveSearchStore<S, T, D> implements RetrieveEntityStore<S, T, K> {

	public DefaultRetrieveEntityStore(final D api, final StoreType storeType) {
		super(api, storeType);
	}

	public DefaultRetrieveEntityStore(final D api, final StoreType storeType, final String qualifier) {
		super(api, storeType, qualifier);
	}

	@Override
	public ServiceStatus getRetrieveStatus(final K key) {
		return getEventStatus(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	public boolean isRetrieveDone(final K key) {
		return isEventDone(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	public T retrieve(final K key) {
		// Retrieve and RetrieveAsync get treated the same
		return (T) getEventResult(RetrieveBaseEventType.RETRIEVE, key);
	}

	@Override
	protected Object doRetrieveServiceCall(final RetrieveEventType type, final Object criteria) {
		if (Objects.equals(type, RetrieveBaseEventType.SEARCH)) {
			return getDataApi().retrieve((K) criteria);
		}
		return super.doRetrieveServiceCall(type, criteria);
	}

}
