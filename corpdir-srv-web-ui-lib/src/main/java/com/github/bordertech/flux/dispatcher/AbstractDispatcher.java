package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.key.ActionKey;
import com.github.bordertech.flux.key.StoreKey;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractDispatcher implements Dispatcher {

	@Override
	public final void dispatch(final Action action) {
		DispatcherUtil.dispatch(action, getDispatcherModel());
	}

	@Override
	public final boolean isDispatching() {
		return getDispatcherModel().isDispatching();
	}

	@Override
	public final String registerListener(final ActionKey matcher, final Listener listener) {
		ListenerWrapper wrapper = new ListenerWrapper(matcher, listener);
		dispatch(new DefaultAction(DispatcherActionType.REGISTER_LISTENER, wrapper));
		return wrapper.getRegisterId();
	}

	@Override
	public final void unregisterListener(final String registerId) {
		dispatch(new DefaultAction(DispatcherActionType.UNREGISTER_LISTENER, registerId));
	}

	@Override
	public Listener getListener(final String registerId) {
		ListenerWrapper wrapper = DispatcherUtil.getListener(registerId, getDispatcherModel());
		return wrapper == null ? null : wrapper.getListener();
	}

	@Override
	public void registerStore(final Store store) {
		dispatch(new DefaultAction(DispatcherActionType.REGISTER_STORE, store));
	}

	@Override
	public void unregisterStore(final StoreKey storeKey) {
		dispatch(new DefaultAction(DispatcherActionType.UNREGISTER_STORE, storeKey));
	}

	@Override
	public Store getStore(final StoreKey storeKey) {
		return DispatcherUtil.getStore(storeKey, getDispatcherModel());
	}

	protected void doConfigModel(final DispatcherModel model) {
		// Register the dispatcher actions
		for (DispatcherActionType actionType : DispatcherActionType.values()) {
			DispatcherUtil.registerDispatcherListener(actionType, model, new Listener<Action>() {
				@Override
				public void handleAction(final Action action) {
					doHandleDispatcherAction(action);
				}
			});
		}
	}

	protected void doHandleDispatcherAction(final Action action) {
		DispatcherActionType type = (DispatcherActionType) action.getKey().getType();
		switch (type) {
			case REGISTER_LISTENER:
				ListenerWrapper wrapper = (ListenerWrapper) action.getData();
				doHandleRegisterListener(wrapper);
				break;
			case UNREGISTER_LISTENER:
				String registerId = (String) action.getData();
				doHandleUnregisterListener(registerId);
				break;
			case REGISTER_STORE:
				Store store = (Store) action.getData();
				doHandleRegisterStore(store);
				break;
			case UNREGISTER_STORE:
				StoreKey storeKey = (StoreKey) action.getData();
				doHandleUnregisterStore(storeKey);
				break;
			default:
				throw new IllegalStateException("Dispatcher action type [" + type + "] not handled.");
		}
	}

	protected void doHandleRegisterListener(final ListenerWrapper wrapper) {
		DispatcherUtil.handleRegisterListener(wrapper, getDispatcherModel());
	}

	protected void doHandleUnregisterListener(final String registerId) {
		DispatcherUtil.handleUnregisterListener(registerId, getDispatcherModel());
	}

	protected void doHandleRegisterStore(final Store store) {
		DispatcherUtil.handleRegisterStore(store, getDispatcherModel());
	}

	protected void doHandleUnregisterStore(final StoreKey storeKey) {
		DispatcherUtil.handleUnregisterStore(storeKey, getDispatcherModel());
	}

	protected abstract DispatcherModel getDispatcherModel();

	protected DispatcherModel createNewModel() {
		DispatcherModel model = new DefaultDispatcherModel();
		doConfigModel(model);
		return model;
	}

}
