package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.crud.action.base.CrudTreeActionBaseType;
import com.github.bordertech.flux.crud.dataapi.CrudTreeApi;
import com.github.bordertech.flux.crud.store.CrudTreeStore;
import com.github.bordertech.taskmaster.RejectedTaskException;
import com.github.bordertech.taskmaster.service.CallType;
import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.taskmaster.service.ServiceAction;
import com.github.bordertech.taskmaster.service.ServiceException;
import com.github.bordertech.taskmaster.service.ServiceHelper;
import java.util.List;
import java.util.Set;

/**
 * Default CRUD Tree Entity Store using a Data API.
 *
 * @param <S> the search type
 * @param <K> the entity key type
 * @param <T> the entity type
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDataApiCrudTreeStore<S, K, T, D extends CrudTreeApi<S, K, T>> extends DefaultDataApiCrudStore<S, K, T, D> implements CrudTreeStore<S, K, T> {

	private static final ServiceHelper SERVICE_HELPER = Didums.getService(ServiceHelper.class);

	public DefaultDataApiCrudTreeStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		super.registerListeners(ids);
		// Action Listeners
		for (CrudTreeActionBaseType type : CrudTreeActionBaseType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleModifyTreeBaseActions(action);
				}
			};
			ids.addAll(registerActionCreatorListeners(type, listener));
		}
	}

	@Override
	public boolean hasChildren(final T item) {
		return getDataApi().hasChildren(item);
	}

	@Override
	public String getItemLabel(final T item) {
		return getDataApi().getItemLabel(item);
	}

	@Override
	public String getItemId(final T item) {
		return getDataApi().getItemId(item);
	}

	protected void handleModifyTreeBaseActions(final Action action) {
		getStoreCache().clear();
	}

	@Override
	public ResultHolder<T, List<T>> getChildren(final T item, final CallType callType) {
		ServiceAction<T, List<T>> action = new ServiceAction<T, List<T>>() {
			@Override
			public List<T> service(final T criteria) {
				return getDataApi().getChildren(criteria);
			}
		};
		K key = getDataApi().getItemKey(item);
		try {
			return SERVICE_HELPER.handleServiceCallType(getStoreCache(), getCacheKey("children", key), item, action, callType);
		} catch (RejectedTaskException e) {
			// Could not start a thread
			return new ResultHolder(item, new ServiceException("Unable to start service. " + e.getMessage(), e));
		} catch (Exception e) {
			// Error calling service
			return new ResultHolder(item, new ServiceException("Error calling service. " + e.getMessage(), e));
		}
	}

	@Override
	public ResultHolder<S, List<T>> getRootItems(final CallType callType) {
		ServiceAction<S, List<T>> action = new ServiceAction<S, List<T>>() {
			@Override
			public List<T> service(final S criteria) {
				return getDataApi().getRootItems();
			}
		};
		try {
			return SERVICE_HELPER.handleServiceCallType(getStoreCache(), getCacheKey("root", ""), null, action, callType);
		} catch (RejectedTaskException e) {
			// Could not start a thread
			return new ResultHolder(null, new ServiceException("Unable to start service. " + e.getMessage(), e));
		} catch (Exception e) {
			// Error calling service
			return new ResultHolder(null, new ServiceException("Error calling service. " + e.getMessage(), e));
		}
	}

}
