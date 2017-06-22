package com.github.bordertech.wcomponents.lib.pub;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultWidget extends WPanel implements Widget {

	@Override
	public List<Class<? extends Event>> getPublisherEvents() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<AjaxTarget> getEventAjaxTargets(final Class<? extends Event> event) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public void handleEvent(final Event event) {
		// Do nothing
	}

	@Override
	public List<Subscriber> getSubscribers() {
		List<Subscriber> subscribers = getComponentModel().subscribers;
		return subscribers == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(subscribers);
	}

	@Override
	public void addSubscriber(final Subscriber subscriber) {
		WidgetModel model = getOrCreateComponentModel();
		if (model.subscribers == null) {
			model.subscribers = new ArrayList<>();
		}
		model.subscribers.add(subscriber);
	}

	@Override
	public void removeSubscriber(final Subscriber subscriber) {
		if (getSubscribers().contains(subscriber)) {
			WidgetModel model = getOrCreateComponentModel();
			model.subscribers.remove(subscriber);
			if (model.subscribers.isEmpty()) {
				model.subscribers = null;
			}
		}
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			initWidget(request);
			setInitialised(true);
		}
	}

	protected void initWidget(final Request request) {
		setupSubscriberAjaxTargets();
	}

	protected void setupSubscriberAjaxTargets() {
		for (Subscriber subscriber : getSubscribers()) {
			for (Class<? extends Event> event : getPublisherEvents()) {
				List<AjaxTarget> targets = subscriber.getEventAjaxTargets(event);
				if (targets != null && !targets.isEmpty()) {
					wireUpEventAjax(targets, event);
				}
			}

		}
	}

	protected void wireUpEventAjax(final List<AjaxTarget> targets, final Class<? extends Event> eventClass) {
		// Do nothing by default
	}

	protected void notifySubscribers(final Event event) {
		for (Subscriber subscriber : getSubscribers()) {
			subscriber.handleEvent(event);
		}
	}

	@Override
	protected WidgetModel newComponentModel() {
		return new WidgetModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WidgetModel getComponentModel() {
		return (WidgetModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WidgetModel getOrCreateComponentModel() {
		return (WidgetModel) super.getOrCreateComponentModel();

	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class WidgetModel extends PanelModel {

		private List<Subscriber> subscribers;
	}

}
