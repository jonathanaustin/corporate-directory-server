package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractWComponent implements BasicController {

	private final Dispatcher dispatcher;

	private final String qualifier;

	public DefaultController(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultController(final Dispatcher dispatcher, final String qualifier) {
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;
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

//	@Override
//	public List<AjaxTarget> getEventTargets(final View view, final EventType eventType) {
//		return Collections.EMPTY_LIST;
//	}
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
			checkConfig();
			configViews();
			setInitialised(true);
		}
	}

	protected void checkConfig() {
	}

	protected void configViews() {
	}

	@Override
	public void configAjax(final BasicView view) {
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
	}

}
