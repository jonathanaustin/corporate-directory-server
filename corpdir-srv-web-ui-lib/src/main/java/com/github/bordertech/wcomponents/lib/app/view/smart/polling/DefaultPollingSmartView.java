package com.github.bordertech.wcomponents.lib.app.view.smart.polling;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.flux.wc.view.DefaultSmartView;
import com.github.bordertech.wcomponents.lib.app.view.PollingView;
import com.github.bordertech.wcomponents.lib.app.view.event.base.PollingBaseViewEvent;
import com.github.bordertech.wcomponents.lib.app.view.polling.DefaultPollingView;

/**
 * Smart View that contains a polling view.
 *
 * @author jonathan
 * @param <T> the item type
 */
public class DefaultPollingSmartView<T> extends DefaultSmartView<T> implements PollingSmartView<T> {

	private final PollingView pollingView = new DefaultPollingView<>("vw-poll");

	public DefaultPollingSmartView(final String viewId, final String templateName) {
		super(viewId, templateName);
		addComponentToTemplate("vw-poll", pollingView);
	}

	@Override
	public PollingView<T> getPollingView() {
		return pollingView;
	}

	@Override
	public void handleViewEvent(final String viewId, final ViewEventType event, final Object data) {
		super.handleViewEvent(viewId, event, data);
		if (event instanceof PollingBaseViewEvent) {
			handlePollingEvents((PollingBaseViewEvent) event, data);
		}
	}

	protected void handlePollingEvents(final PollingBaseViewEvent type, final Object data) {

		switch (type) {
			case STARTED:
				handlePollingStartedEvent();
				break;
			case STOPPED:
				handlePollingStoppedEvent();
				break;
		}

	}

	protected void handlePollingStartedEvent() {
	}

	protected void handlePollingStoppedEvent() {
	}

}
