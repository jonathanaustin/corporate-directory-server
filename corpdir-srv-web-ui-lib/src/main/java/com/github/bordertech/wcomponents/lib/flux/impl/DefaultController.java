package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.event.ActionEventType;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.flux.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractWComponent implements WController {

	private final Dispatcher dispatcher;

	private final String qualifier;

	public DefaultController(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultController(final Dispatcher dispatcher, final String qualifier) {
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;

		// Default Listeners
		// Reset EVENT
		Listener listener = new Listener() {
			@Override
			public void handleEvent(final Event event) {
				handleResetEvent();
			}
		};
		registerCtrlListener(listener, ActionEventType.RESET);
	}

	@Override
	public final Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public final String getQualifier() {
		return qualifier;
	}

	@Override
	public final WMessages getViewMessages() {
		return WMessages.getInstance(this);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	public final void dispatchCtrlEvent(final EventType eventType) {
		dispatchCtrlEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	public void dispatchCtrlEvent(final EventType eventType, final Object data) {
		dispatchCtrlEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	public void dispatchCtrlEvent(final EventType eventType, final Object data, final Exception exception) {
		Event event = new Event(null, new EventQualifier(eventType, getQualifier()), data, exception);
		getDispatcher().dispatch(event);
	}

	/**
	 * A helper method to register a listener with an Event Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 * @return the listener register id
	 */
	public final String registerCtrlListener(final Listener listener, final EventType eventType) {
		return getDispatcher().register(listener, new EventMatcher(eventType, getQualifier()));
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			resetViews();
			configViews();
			setInitialised(true);
		}
	}

	@Override
	public void configViews() {
		checkConfig();
	}

	@Override
	public void configAjax(final WView view) {
	}

	@Override
	public void reset() {
		resetViews();
		super.reset();
	}

	@Override
	public List<View> getViews() {
		CtrlModel model = getComponentModel();
		return model.views == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(model.views);
	}

	@Override
	public void addView(final WView view) {
		view.setController(this);
		CtrlModel model = getOrCreateComponentModel();
		if (model.views == null) {
			model.views = new ArrayList<>();
		}
		model.views.add(view);
	}

	@Override
	public void removeView(final WView view) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.views != null) {
			model.views.remove(view);
			if (model.views.isEmpty()) {
				model.views = null;
			}
		}
	}

	@Override
	public void resetViews() {
		for (View view : getViews()) {
			view.resetView();
		}
	}

	protected void checkConfig() {
	}

	protected void handleResetEvent() {
		reset();
	}

	@Override
	protected CtrlModel newComponentModel() {
		return new CtrlModel();
	}

	@Override
	protected CtrlModel getComponentModel() {
		return (CtrlModel) super.getComponentModel();
	}

	@Override
	protected CtrlModel getOrCreateComponentModel() {
		return (CtrlModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class CtrlModel extends ComponentModel {

		private List<View> views;
	}

}
