package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.crud.action.base.CrudActionBaseType;
import com.github.bordertech.flux.crud.dataapi.CrudApi;
import com.github.bordertech.flux.crud.store.DataApiCrudStore;
import com.github.bordertech.flux.store.impl.DefaultSearchDataApiStore;
import com.github.bordertech.taskmaster.RejectedTaskException;
import com.github.bordertech.taskmaster.service.CallType;
import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.taskmaster.service.ServiceAction;
import com.github.bordertech.taskmaster.service.ServiceException;
import com.github.bordertech.taskmaster.service.ServiceHelper;
import java.util.Set;

/**
 * Default CRUD Entity Store using a Data API.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the API data type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiCrudStore<S, K, T, D extends CrudApi<S, K, T>> extends DefaultSearchDataApiStore<S, T, D> implements DataApiCrudStore<S, K, T, D> {

	private static final ServiceHelper SERVICE_HELPER = Didums.getService(ServiceHelper.class);

	public DefaultDataApiCrudStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		super.registerListeners(ids);
		// Action Listeners
		for (CrudActionBaseType type : CrudActionBaseType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleModifyBaseActions(action);
				}
			};
			ids.addAll(registerActionCreatorListeners(type, listener));
		}
	}

	protected void handleModifyBaseActions(final Action action) {
		// Default to clear cache
		getStoreCache().clear();
	}

	@Override
	public ResultHolder<K, T> fetch(final K entityKey, final CallType callType) {
		ServiceAction<K, T> action = new ServiceAction<K, T>() {
			@Override
			public T service(final K criteria) {
				return getDataApi().retrieve(criteria);
			}
		};
		try {
			return SERVICE_HELPER.handleServiceCallType(getStoreCache(), getCacheKey("fetch", entityKey), entityKey, action, callType);
		} catch (RejectedTaskException e) {
			// Could not start a thread
			return new ResultHolder(entityKey, new ServiceException("Unable to start service. " + e.getMessage(), e));
		} catch (Exception e) {
			// Error calling service
			return new ResultHolder(entityKey, new ServiceException("Error calling service. " + e.getMessage(), e));
		}
	}

	@Override
	public K getItemKey(final T item) {
		return getDataApi().getItemKey(item);
	}

}
