package com.github.bordertech.flux.impl;

import com.github.bordertech.flux.Dispatcher;
import com.github.bordertech.flux.Event;
import com.github.bordertech.flux.EventKey;
import com.github.bordertech.flux.Listener;
import com.github.bordertech.flux.Store;
import com.github.bordertech.flux.StoreKey;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class DefaultDispatcher implements Dispatcher {

	@Override
	public final void dispatch(final Event event) {
		DispatcherUtil.dispatch(event, getDispatcherModel());
	}

	@Override
	public final boolean isDispatching() {
		return getDispatcherModel().isDispatching();
	}

	@Override
	public final String registerListener(final EventKey matcher, final Listener listener) {
		ListenerWrapper wrapper = new ListenerWrapper(matcher, listener);
		dispatch(new DefaultEvent(DispatcherEventType.REGISTER_LISTENER, wrapper));
		return wrapper.getRegisterId();
	}

	@Override
	public final void unregisterListener(final String registerId) {
		dispatch(new DefaultEvent(DispatcherEventType.UNREGISTER_LISTENER, registerId));
	}

	@Override
	public Listener getListener(final String registerId) {
		ListenerWrapper wrapper = DispatcherUtil.getListener(registerId, getDispatcherModel());
		return wrapper == null ? null : wrapper.getListener();
	}

	@Override
	public void registerStore(final Store store) {
		dispatch(new DefaultEvent(DispatcherEventType.REGISTER_STORE, store));
	}

	@Override
	public void unregisterStore(final StoreKey storeKey) {
		dispatch(new DefaultEvent(DispatcherEventType.UNREGISTER_STORE, storeKey));
	}

	@Override
	public Store getStore(final StoreKey storeKey) {
		return DispatcherUtil.getStore(storeKey, getDispatcherModel());
	}

	protected void doConfigModel(final DispatcherModel model) {
		// Register the dispatcher events
		for (DispatcherEventType eventType : DispatcherEventType.values()) {
			DispatcherUtil.registerDispatcherListener(eventType, model, new Listener<Event>() {
				@Override
				public void handleEvent(final Event event) {
					doHandleDispatcherEvent(event);
				}
			});
		}
	}

	protected void doHandleDispatcherEvent(final Event event) {
		DispatcherEventType type = (DispatcherEventType) event.getEventKey().getEventType();
		switch (type) {
			case REGISTER_LISTENER:
				ListenerWrapper wrapper = (ListenerWrapper) event.getData();
				doHandleRegisterListener(wrapper);
				break;
			case UNREGISTER_LISTENER:
				String registerId = (String) event.getData();
				doHandleUnregisterListener(registerId);
				break;
			case REGISTER_STORE:
				Store store = (Store) event.getData();
				doHandleRegisterStore(store);
				break;
			case UNREGISTER_STORE:
				StoreKey storeKey = (StoreKey) event.getData();
				doHandleUnregisterStore(storeKey);
				break;
			default:
				throw new IllegalStateException("Dispatcher event type [" + type + "] not handled.");
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
