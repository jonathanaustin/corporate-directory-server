package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.key.ActionKey;

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
	public void unregisterStore(final String storeKey) {
		dispatch(new DefaultAction(DispatcherActionType.UNREGISTER_STORE, storeKey));
	}

	@Override
	public Store getStore(final String storeKey) {
		return DispatcherUtil.getStore(storeKey, getDispatcherModel());
	}

	@Override
	public void registerActionCreator(final ActionCreator creator) {
		dispatch(new DefaultAction(DispatcherActionType.REGISTER_CREATOR, creator));
	}

	@Override
	public void unregisterActionCreator(final String creatorKey) {
		dispatch(new DefaultAction(DispatcherActionType.UNREGISTER_CREATOR, creatorKey));
	}

	@Override
	public ActionCreator getActionCreator(final String creatorKey) {
		return DispatcherUtil.getActionCreator(creatorKey, getDispatcherModel());
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
				DispatcherUtil.handleRegisterListener(wrapper, getDispatcherModel());
				break;
			case UNREGISTER_LISTENER:
				String registerId = (String) action.getData();
				DispatcherUtil.handleUnregisterListener(registerId, getDispatcherModel());
				break;
			case REGISTER_STORE:
				Store store = (Store) action.getData();
				DispatcherUtil.handleRegisterStore(store, getDispatcherModel());
				break;
			case UNREGISTER_STORE:
				String storeKey = (String) action.getData();
				DispatcherUtil.handleUnregisterStore(storeKey, getDispatcherModel());
				break;
			case REGISTER_CREATOR:
				ActionCreator creator = (ActionCreator) action.getData();
				DispatcherUtil.handleRegisterActionCreator(creator, getDispatcherModel());
				break;
			case UNREGISTER_CREATOR:
				String creatorKey = (String) action.getData();
				DispatcherUtil.handleUnregisterActionCreator(creatorKey, getDispatcherModel());
				break;
			default:
				throw new IllegalStateException("Dispatcher action type [" + type + "] not handled.");
		}
	}

	protected abstract DispatcherModel getDispatcherModel();

	protected DispatcherModel createNewModel() {
		DispatcherModel model = new DefaultDispatcherModel();
		doConfigModel(model);
		return model;
	}

}
