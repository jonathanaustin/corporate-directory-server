package com.github.bordertech.wcomponents.lib.flux;

import com.github.bordertech.wcomponents.lib.WDiv;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDispatcher extends WDiv implements Dispatcher {

	@Override
	public void dispatch(final Event event) {
		for (Listener listener : getListeners(event.getEventType())) {
			listener.handleEvent(event);
		}
	}

	@Override
	public List<Listener> getListeners() {
		List<Listener> listeners = getComponentModel().listeners;
		return listeners == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(listeners);
	}

	@Override
	public List<Listener> getListeners(final EventType eventType) {
		List<Listener> listeners = new ArrayList<>();
		for (Listener listener : getListeners()) {
			if (Objects.equals(eventType, listener.getEventType())) {
				listeners.add(listener);
			}
		}
		return Collections.unmodifiableList(listeners);
	}

	@Override
	public void addListener(final Listener listener) {
		DispatcherModel model = getOrCreateComponentModel();
		if (model.listeners == null) {
			model.listeners = new ArrayList<>();
		}
		model.listeners.add(listener);
	}

	@Override
	public void removeListener(final Listener listener) {
		if (getListeners().contains(listener)) {
			DispatcherModel model = getOrCreateComponentModel();
			model.listeners.remove(listener);
			if (model.listeners.isEmpty()) {
				model.listeners = null;
			}
		}
	}

	@Override
	protected DispatcherModel newComponentModel() {
		return new DispatcherModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DispatcherModel getComponentModel() {
		return (DispatcherModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected DispatcherModel getOrCreateComponentModel() {
		return (DispatcherModel) super.getOrCreateComponentModel();

	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class DispatcherModel extends DivModel {

		private List<Listener> listeners;
	}

}
