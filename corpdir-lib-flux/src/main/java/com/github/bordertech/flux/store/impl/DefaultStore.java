package com.github.bordertech.flux.store.impl;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.ActionKey;
import com.github.bordertech.flux.action.ActionType;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.type.ModifyStoreActionType;
import com.github.bordertech.flux.action.type.base.RetrieveOutcomeBaseActionType;
import com.github.bordertech.flux.action.type.base.StateBaseActionType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.cache.Cache;

/**
 * Default Store.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultStore implements Store {

	private final Set<String> actionCreatorKeys;

	private static final Dispatcher DISPATCHER = Didums.getService(Dispatcher.class);

	private final String key;

	public DefaultStore(final String storeKey) {
		this(storeKey, Collections.EMPTY_SET);
	}

	public DefaultStore(final String storeKey, final Set<String> actionCreatorKeys) {
		this.key = storeKey;
		this.actionCreatorKeys = actionCreatorKeys;
	}

	@Override
	public final String getKey() {
		return key;
	}

	@Override
	public final Set<String> getActionCreatorKeys() {
		return actionCreatorKeys;
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DISPATCHER;
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		// Outcome Listeners
		for (RetrieveOutcomeBaseActionType type : RetrieveOutcomeBaseActionType.values()) {
			Listener listener = new Listener() {
				@Override
				public void handleAction(final Action action) {
					handleRetrieveOutcomeBaseActions(action);
				}
			};
			String id = registerListener(type, listener);
			ids.add(id);
		}
	}

	protected void handleRetrieveOutcomeBaseActions(final Action action) {
		RetrieveOutcomeBaseActionType type = (RetrieveOutcomeBaseActionType) action.getKey().getType();
		boolean changed = false;
		switch (type) {
			case ASYNC_ERROR:
				handleAsyncErrorAction((ResultHolder) action.getData());
				changed = true;
				break;
			case ASYNC_OK:
				handleAsyncOKAction((ResultHolder) action.getData());
				changed = true;
				break;
			default:
				changed = false;
		}
		if (changed) {
			dispatchChangeAction(type);
		}
	}

	protected void handleAsyncOKAction(final ResultHolder holder) {
		// OK
	}

	protected void handleAsyncErrorAction(final ResultHolder holder) {
		// ERROR
	}

	/**
	 * A helper method to register a listener for an Action Creator.
	 *
	 * @param listener the listener to register
	 * @param actionType the action type
	 * @return the listener id
	 */
	protected Set<String> registerActionCreatorListeners(final ModifyStoreActionType actionType, final Listener listener) {
		Set<String> ids = new HashSet<>();
		for (String acKey : getActionCreatorKeys()) {
			String id = registerListener(actionType, acKey, listener);
			ids.add(id);
		}
		return ids;
	}

	/**
	 * A helper method to register a listener with an Action Type and the Controller qualifier automatically added.
	 *
	 * @param actionType the action type
	 * @param listener the listener to register
	 * @return the listener id
	 */
	protected String registerListener(final ActionType actionType, final Listener listener) {
		return registerListener(actionType, getKey(), listener);

	}

	/**
	 * A helper method to register a listener with an Action Type.
	 *
	 * @param actionType the action type
	 * @param key the qualifier
	 * @param listener the listener to register
	 * @return the listener id
	 */
	protected String registerListener(final ActionType actionType, final String key, final Listener listener) {
		return getDispatcher().registerListener(new ActionKey(actionType, key), listener);
	}

	protected void dispatchAsyncDone(final ResultHolder resultHolder) {
		if (resultHolder.isException()) {
			dispatchAction(RetrieveOutcomeBaseActionType.ASYNC_ERROR, resultHolder);
		} else {
			dispatchAction(RetrieveOutcomeBaseActionType.ASYNC_OK, resultHolder);
		}
	}

	protected void dispatchChangeAction(final ActionType actionType) {
		dispatchAction(StateBaseActionType.STORE_CHANGED, actionType);
	}

	protected void dispatchAction(final ActionType actionType, final Object data) {
		DefaultAction action = new DefaultAction(actionType, getKey(), data);
		getDispatcher().dispatch(action);
	}

	/**
	 * A Store that can be used to store service results.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<String, ResultHolder> getStoreCache() {
		return ServiceUtil.getResultHolderCache("flux-default-" + getKey());
	}

	protected String getCacheKey(final String action, final Object criteria) {
		String suffix = criteria == null ? "-null" : "-c-" + criteria.toString();
		return getKey() + "-" + action + "-" + suffix;
	}

}
