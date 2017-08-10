package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.pub.Event;
import com.github.bordertech.wcomponents.lib.pub.Subscriber;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends WDiv implements View {

	private final WDiv holder = new WDiv() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	@Override
	public final WDiv getViewHolder() {
		return holder;
	}

	protected void initViewContent(final Request request) {
		// Do nothing
	}

	@Override
	public List<AjaxTarget> getEventTargets(final Class<? extends Event> event) {
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
		ViewModel model = getOrCreateComponentModel();
		if (model.subscribers == null) {
			model.subscribers = new ArrayList<>();
		}
		model.subscribers.add(subscriber);
	}

	@Override
	public void removeSubscriber(final Subscriber subscriber) {
		if (getSubscribers().contains(subscriber)) {
			ViewModel model = getOrCreateComponentModel();
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
		for (Subscriber subscriber : getSubscribers()) {
			wireUpSubscriberAjax(subscriber);
		}
	}

	protected void wireUpSubscriberAjax(final Subscriber subscriber) {
		// Do nothing by default
	}

	@Override
	public void notifySubscribers(final Event event) {
		for (Subscriber subscriber : getSubscribers()) {
			subscriber.handleEvent(event);
		}
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();

	}

	/**
	 * Holds the extrinsic state information of the edit view.
	 */
	public static class ViewModel extends DivModel {

		private List<Subscriber> subscribers;
	}

}