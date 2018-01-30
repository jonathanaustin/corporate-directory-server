package com.github.bordertech.flux.store.impl;

import com.github.bordertech.flux.dataapi.SearchApi;
import com.github.bordertech.flux.store.SearchStore;
import com.github.bordertech.taskmanager.service.CallType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.List;
import java.util.Set;

/**
 * Default search store.
 *
 * @param <S> the criteria type
 * @param <T> the item type
 * @param <D> the data API type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public class DefaultSearchDataApiStore<S, T, D extends SearchApi<S, T>> extends DefaultDataApiStore<D> implements SearchStore<S, T> {

	public DefaultSearchDataApiStore(final String storeKey, final Set<String> actionCreatorKeys, final D api) {
		super(storeKey, actionCreatorKeys, api);
	}

	@Override
	public ResultHolder<S, List<T>> search(final S criteria, final CallType callType) {
		ServiceAction<S, List<T>> action = new ServiceAction<S, List<T>>() {
			@Override
			public List<T> service(final S criteria) {
				return getDataApi().search(criteria);
			}
		};
		return ServiceUtil.handleServiceCallType(getStoreCache(), getCacheKey("search", criteria), criteria, action, callType);
	}

}
