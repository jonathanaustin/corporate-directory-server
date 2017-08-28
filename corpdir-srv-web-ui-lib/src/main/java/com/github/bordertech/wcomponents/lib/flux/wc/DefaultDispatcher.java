package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.Matcher;
import com.github.bordertech.wcomponents.lib.flux.util.DispatcherEventType;
import com.github.bordertech.wcomponents.lib.flux.util.DispatcherUtil;
import com.github.bordertech.wcomponents.lib.flux.util.ListenerWrapper;
import com.github.bordertech.wcomponents.lib.flux.util.RegisterEvent;
import com.github.bordertech.wcomponents.lib.flux.util.UnregisterEvent;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDispatcher extends AbstractWComponent implements Dispatcher {

	public DefaultDispatcher() {

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

		DispatcherComponentModel model = getOrCreateComponentModel();
		DispatcherUtil.registerDispatcherListener(regListener, DispatcherEventType.REGISTER, model);
		DispatcherUtil.registerDispatcherListener(unregListener, DispatcherEventType.UNREGISTER, model);
	}

	@Override
	public final void dispatch(final Event event) {
		DispatcherUtil.dispatch(event, getOrCreateComponentModel());
	}

	@Override
	public final boolean isDispatching() {
		return getComponentModel().isDispatching();
	}

	@Override
	public final String register(final Listener listener, final EventType eventType) {
		return register(listener, new EventMatcher(eventType));
	}

	@Override
	public final String register(final Listener listener, final String qualifier) {
		return register(listener, new EventMatcher(qualifier));
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
		ListenerWrapper wrapper = DispatcherUtil.getListener(registerId, getComponentModel());
		return wrapper == null ? null : wrapper.getListener();
	}

	protected void doHandleRegisterListener(final ListenerWrapper wrapper) {
		DispatcherUtil.handleRegisterListener(wrapper, getOrCreateComponentModel());
	}

	protected void doHandleUnregisterListener(final String registerId) {
		// Check it exists
		if (getListener(registerId) != null) {
			// Unregister
			DispatcherUtil.handleUnregisterListener(registerId, getOrCreateComponentModel());
		}
	}

	@Override
	protected DispatcherComponentModel newComponentModel() {
		return new DispatcherComponentModel();
	}

	@Override
	protected DispatcherComponentModel getComponentModel() {
		return (DispatcherComponentModel) super.getComponentModel();
	}

	@Override
	protected DispatcherComponentModel getOrCreateComponentModel() {
		return (DispatcherComponentModel) super.getOrCreateComponentModel();
	}

}
