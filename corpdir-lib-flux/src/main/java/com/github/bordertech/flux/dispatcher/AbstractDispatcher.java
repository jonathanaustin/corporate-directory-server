package com.github.bordertech.flux.dispatcher;

import com.github.bordertech.flux.Action;
import com.github.bordertech.flux.ActionCreator;
import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.action.ActionKey;
import com.github.bordertech.flux.action.DefaultAction;
import com.github.bordertech.flux.action.type.base.DispatcherBaseActionType;

/**
 * Partial implementation of Dispatcher.
 * <p>
 * Concrete implementations need to provide how the dispatcher model state is held.
 * </p>
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractDispatcher implements Dispatcher {

	@Override
	public final void dispatch(final Action action) {
		DispatcherModelUtil.dispatch(action, getDispatcherModel());
	}

	@Override
	public final boolean isDispatching() {
		return getDispatcherModel().isDispatching();
	}

	@Override
	public final String registerListener(final ActionKey matcher, final Listener listener) {
		ListenerWrapper wrapper = new ListenerWrapper(matcher, listener);
		dispatch(new DefaultAction(DispatcherBaseActionType.REGISTER_LISTENER, wrapper));
		return wrapper.getRegisterId();
	}

	@Override
	public final void unregisterListener(final String registerId) {
		dispatch(new DefaultAction(DispatcherBaseActionType.UNREGISTER_LISTENER, registerId));
	}

	@Override
	public Listener getListener(final String registerId) {
		ListenerWrapper wrapper = DispatcherModelUtil.getListener(registerId, getDispatcherModel());
		return wrapper == null ? null : wrapper.getListener();
	}

	@Override
	public void registerStore(final Store store) {
		dispatch(new DefaultAction(DispatcherBaseActionType.REGISTER_STORE, store));
	}

	@Override
	public void unregisterStore(final String storeKey) {
		dispatch(new DefaultAction(DispatcherBaseActionType.UNREGISTER_STORE, storeKey));
	}

	@Override
	public Store getStore(final String storeKey) {
		return DispatcherModelUtil.getStore(storeKey, getDispatcherModel());
	}

	@Override
	public void registerActionCreator(final ActionCreator creator) {
		dispatch(new DefaultAction(DispatcherBaseActionType.REGISTER_CREATOR, creator));
	}

	@Override
	public void unregisterActionCreator(final String creatorKey) {
		dispatch(new DefaultAction(DispatcherBaseActionType.UNREGISTER_CREATOR, creatorKey));
	}

	@Override
	public ActionCreator getActionCreator(final String creatorKey) {
		return DispatcherModelUtil.getActionCreator(creatorKey, getDispatcherModel());
	}

	protected void doConfigModel(final DispatcherModel model) {
		// Register the dispatcher actions
		for (DispatcherBaseActionType actionType : DispatcherBaseActionType.values()) {
			DispatcherModelUtil.registerDispatcherListener(actionType, model, new Listener<Action>() {
				@Override
				public void handleAction(final Action action) {
					doHandleDispatcherAction(action);
				}
			});
		}
	}

	protected void doHandleDispatcherAction(final Action action) {
		DispatcherBaseActionType type = (DispatcherBaseActionType) action.getKey().getType();
		switch (type) {
			case REGISTER_LISTENER:
				ListenerWrapper wrapper = (ListenerWrapper) action.getData();
				DispatcherModelUtil.handleRegisterListener(wrapper, getDispatcherModel());
				break;
			case UNREGISTER_LISTENER:
				String registerId = (String) action.getData();
				DispatcherModelUtil.handleUnregisterListener(registerId, getDispatcherModel());
				break;
			case REGISTER_STORE:
				Store store = (Store) action.getData();
				DispatcherModelUtil.handleRegisterStore(store, getDispatcherModel());
				break;
			case UNREGISTER_STORE:
				String storeKey = (String) action.getData();
				DispatcherModelUtil.handleUnregisterStore(storeKey, getDispatcherModel());
				break;
			case REGISTER_CREATOR:
				ActionCreator creator = (ActionCreator) action.getData();
				DispatcherModelUtil.handleRegisterActionCreator(creator, getDispatcherModel());
				break;
			case UNREGISTER_CREATOR:
				String creatorKey = (String) action.getData();
				DispatcherModelUtil.handleUnregisterActionCreator(creatorKey, getDispatcherModel());
				break;
			default:
				throw new IllegalStateException("Dispatcher action type [" + type + "] not handled.");
		}
	}

	protected abstract DispatcherModel getDispatcherModel();

	protected DispatcherModel createNewModel() {
		DispatcherModel model = new DispatcherModel();
		doConfigModel(model);
		return model;
	}

}
