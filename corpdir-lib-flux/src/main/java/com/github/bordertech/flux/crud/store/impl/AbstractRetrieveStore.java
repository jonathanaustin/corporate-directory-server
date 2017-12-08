package com.github.bordertech.flux.crud.store.impl;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.crud.action.ModifyActionType;
import com.github.bordertech.flux.crud.action.RetrieveActionType;
import com.github.bordertech.flux.crud.action.base.AsyncOutcomeBaseActionType;
import com.github.bordertech.flux.crud.action.base.EntityActionBaseType;
import com.github.bordertech.flux.crud.action.base.RetrieveActionBaseType;
import com.github.bordertech.flux.crud.action.retrieve.CallType;
import com.github.bordertech.flux.crud.action.retrieve.RetrieveAction;
import com.github.bordertech.flux.crud.store.RetrieveActionException;
import com.github.bordertech.flux.crud.store.RetrieveActionStore;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.store.DefaultStore;
import com.github.bordertech.taskmanager.service.AsyncException;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceAction;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract retrieve store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public abstract class AbstractRetrieveStore extends DefaultStore implements RetrieveActionStore {

	private final Set<String> actionCreatorKeys;

	public AbstractRetrieveStore(final String storeKey) {
		this(storeKey, Collections.EMPTY_SET);
	}

	public AbstractRetrieveStore(final String storeKey, final Set<String> actionCreatorKeys) {
		super(storeKey);
		this.actionCreatorKeys = actionCreatorKeys;
	}

	public final Set<String> getActionCreatorKeys() {
		return actionCreatorKeys;
	}

	@Override
	public boolean isAsyncDone(final RetrieveActionType type, final Object criteria) throws AsyncException {
		String key = getResultCacheKey(type, criteria);
		// Check if async result available
		ResultHolder resultHolder = ServiceUtil.checkASyncResult(getStoreCache(), key);
		if (resultHolder == null) {
			return false;
		}
		if (resultHolder.isException()) {
			dispatchResultAction(AsyncOutcomeBaseActionType.ASYNC_ERROR, resultHolder);
		} else {
			dispatchResultAction(AsyncOutcomeBaseActionType.ASYNC_OK, resultHolder);
		}
		return true;
	}

	@Override
	public Object getActionResultCacheOnly(final RetrieveActionType type, final Object criteria) throws RetrieveActionException {
		// Check if have cached result
		ResultHolder<?, ?> holder = getResultHolder(type, criteria);
		if (holder == null) {
			return null;
		}
		return extractResult(holder);
	}

	@Override
	public Object getActionResult(final RetrieveActionType type, final Object criteria) throws RetrieveActionException {
		// Check if have cached result
		ResultHolder<?, ?> holder = getResultHolder(type, criteria);
		if (holder == null) {
			// Call SYNC (ie retrieve immediately)
			holder = handleServiceCallSyncAction(type, criteria);
		}
		return extractResult(holder);
	}

	protected Object extractResult(final ResultHolder<?, ?> holder) throws RetrieveActionException {
		if (holder.isException()) {
			Exception excp = holder.getException();
			if (excp instanceof RetrieveActionException) {
				throw (RetrieveActionException) excp;
			}
			throw new RetrieveActionException(excp.getMessage(), excp);
		}
		return holder.getResult();
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		// Retrieve Listeners
		for (RetrieveActionBaseType type : RetrieveActionBaseType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleRetrieveBaseActions((RetrieveAction) action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
		// Async Outcome Listeners
		for (AsyncOutcomeBaseActionType type : AsyncOutcomeBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleAsyncOutcomeBaseActions((RetrieveAction) action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
		// Action Listeners
		for (EntityActionBaseType type : EntityActionBaseType.values()) {
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
		ServiceUtil.clearCache(getStoreCache());
	}

	protected void handleRetrieveBaseActions(final RetrieveAction action) {
		RetrieveActionBaseType type = (RetrieveActionBaseType) action.getKey().getType();
		CallType callType = action.getCallType();
		boolean changed = false;
		switch (callType) {
			case CALL_SYNC:
			case REFRESH_SYNC:
				handleServiceCallSyncAction(type, action.getData());
				changed = true;
				break;

			case CALL_ASYNC:
			case REFRESH_ASYNC:
				handleServiceCallASyncAction(type, action.getData());
				break;
			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected ResultHolder handleServiceCallSyncAction(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = new ServiceAction() {
			@Override
			public Object service(final Object criteria) {
				return doRetrieveDataApiCall(type, criteria);
			}
		};
		return ServiceUtil.handleCachedServiceCall(getStoreCache(), key, criteria, action);
	}

	protected void handleServiceCallASyncAction(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		ServiceAction action = new ServiceAction() {
			@Override
			public Object service(final Object criteria) {
				return doRetrieveDataApiCall(type, criteria);
			}
		};
		ServiceUtil.handleAsyncServiceCall(getStoreCache(), key, criteria, action);
	}

	protected void handleAsyncOutcomeBaseActions(final RetrieveAction action) {
		AsyncOutcomeBaseActionType type = (AsyncOutcomeBaseActionType) action.getKey().getType();
		boolean changed = false;
		switch (type) {
			case ASYNC_ERROR:
				handleAsyncErrorAction(type, (ResultHolder) action.getData());
				changed = true;
				break;
			case ASYNC_OK:
				handleAsyncOKAction(type, (ResultHolder) action.getData());
				changed = true;
				break;
			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected void handleAsyncOKAction(final RetrieveActionType type, final ResultHolder<?, ?> holder) {
		// OK
	}

	protected void handleAsyncErrorAction(final RetrieveActionType type, final ResultHolder<?, ?> holder) {
		// ERROR
	}

	protected void handleRefreshAction(final RetrieveActionType type, final Object criteria, final boolean async) {
		// Clear from the cache
		String key = getResultCacheKey(type, criteria);
		ServiceUtil.clearResult(getStoreCache(), key);
		if (async) {
			handleServiceCallASyncAction(type, criteria);
		} else {
			handleServiceCallSyncAction(type, criteria);
		}
	}

	protected String getResultCacheKey(final RetrieveActionType type, final Object criteria) {
		String typeDesc = type.toString();
		String suffix = criteria == null ? "" : criteria.toString();
		return getKey() + "-" + typeDesc + "-" + suffix;
	}

	protected ResultHolder<?, ?> getResultHolder(final RetrieveActionType type, final Object criteria) {
		String key = getResultCacheKey(type, criteria);
		return ServiceUtil.getResultHolder(getStoreCache(), key);
	}

	/**
	 * Helper method to dispatch an action for this view with the view qualifier automatically added.
	 *
	 * @param actionType the action type
	 * @param result the action data
	 */
	protected void dispatchResultAction(final RetrieveActionType actionType, final ResultHolder<?, ?> result) {
		String qualifier = getKey();
		DefaultAction action = new RetrieveAction(actionType, qualifier, result, null);
		getDispatcher().dispatch(action);
	}

	/**
	 * A helper method to register a listener with an Action Type and the Controller qualifier automatically added.
	 *
	 * @param listener the listener to register
	 * @param actionType the action type
	 * @return the listener id
	 */
	protected String registerListener(final RetrieveActionType actionType, final Listener listener) {
		return getDispatcher().registerListener(new ActionKey(actionType, getKey()), listener);
	}

	/**
	 * A helper method to register a listener for an Action Creator.
	 *
	 * @param listener the listener to register
	 * @param actionType the action type
	 * @return the listener id
	 */
	protected Set<String> registerActionCreatorListeners(final ModifyActionType actionType, final Listener listener) {
		Set<String> ids = new HashSet<>();
		Dispatcher dispatcher = getDispatcher();
		for (String acKey : getActionCreatorKeys()) {
			String id = dispatcher.registerListener(new ActionKey(actionType, acKey), listener);
			ids.add(id);
		}
		return ids;
	}

	/**
	 *
	 * @param type the action type
	 * @param criteria the criteria
	 * @return the result
	 */
	protected abstract Object doRetrieveDataApiCall(final RetrieveActionType type, final Object criteria);

}
