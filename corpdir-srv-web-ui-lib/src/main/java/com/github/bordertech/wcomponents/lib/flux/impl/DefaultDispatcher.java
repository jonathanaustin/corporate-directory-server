package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.Matcher;

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
	public final String register(final Listener listener, final Matcher matcher) {
		ListenerWrapper wrapper = new ListenerWrapper(matcher, listener);
		dispatch(new RegisterEvent(wrapper));
		return wrapper.getRegisterId();
	}

	@Override
	public final void unregister(final String registerId) {
		dispatch(new UnregisterEvent(registerId));
	}

	@Override
	public Listener getListener(final String registerId) {
		ListenerWrapper wrapper = DispatcherUtil.getListener(registerId, getDispatcherModel());
		return wrapper == null ? null : wrapper.getListener();
	}

	protected void doConfigModel(final DispatcherModel model) {
		// Listen for Register Event
		Listener regListener = new Listener<RegisterEvent>() {
			@Override
			public void handleEvent(final RegisterEvent event) {
				doHandleRegisterListener(event.getWrapper());
			}
		};

		// Listen for Unregister Event
		Listener unregListener = new Listener<UnregisterEvent>() {
			@Override
			public void handleEvent(final UnregisterEvent event) {
				doHandleUnregisterListener(event.getRegisterId());
			}
		};
		DispatcherUtil.registerDispatcherListener(regListener, DispatcherEventType.REGISTER, model);
		DispatcherUtil.registerDispatcherListener(unregListener, DispatcherEventType.UNREGISTER, model);
	}

	protected void doHandleRegisterListener(final ListenerWrapper wrapper) {
		DispatcherUtil.handleRegisterListener(wrapper, getDispatcherModel());
	}

	protected void doHandleUnregisterListener(final String registerId) {
		// Check it exists
		if (getListener(registerId) != null) {
			// Unregister
			DispatcherUtil.handleUnregisterListener(registerId, getDispatcherModel());
		}
	}

	protected abstract DispatcherModel getDispatcherModel();

	protected DispatcherModel createNewModel() {
		DispatcherModel model = new DefaultDispatcherModel();
		doConfigModel(model);
		return model;
	}

}
