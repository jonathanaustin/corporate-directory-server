package com.github.bordertech.flux.store;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.base.StateBaseActionType;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.ActionType;
import com.github.bordertech.taskmanager.service.ResultHolder;
import com.github.bordertech.taskmanager.service.ServiceUtil;
import java.util.Set;
import javax.cache.Cache;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultStore implements Store {

	private static final Dispatcher DISPATCHER = Didums.getService(Dispatcher.class);

	private final String key;

	public DefaultStore(final String key) {
		this.key = key;
	}

	@Override
	public final String getKey() {
		return key;
	}

	@Override
	public void registerListeners(final Set<String> ids) {
		// Do nothing
	}

	@Override
	public void dispatchChangeAction(final ActionType actionType) {
		DefaultAction action = new DefaultAction(new ActionKey(StateBaseActionType.STORE_CHANGED, getKey()), actionType);
		getDispatcher().dispatch(action);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return DISPATCHER;
	}

	/**
	 * Use a cache to hold a reference to the future so the user context can be serialized. Future Objects are not
	 * serializable.
	 *
	 * @return the cache instance
	 */
	protected synchronized Cache<String, ResultHolder> getStoreCache() {
		return ServiceUtil.getResultHolderCache("flux-default-" + getKey());
	}

}
