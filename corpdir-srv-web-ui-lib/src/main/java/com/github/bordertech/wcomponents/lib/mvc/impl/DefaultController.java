package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.lib.model.Model;
import com.github.bordertech.wcomponents.lib.mvc.Controller;
import com.github.bordertech.wcomponents.lib.mvc.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.github.bordertech.wcomponents.lib.mvc.ComboView;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractWComponent implements Controller {

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
		registerCtrlListener(listener, ControllerEventType.RESET);
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

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isConfigured()) {
			throw new IllegalStateException("configViews has not been called on the controller");
		}
	}

	@Override
	public void checkConfig() {
		setConfigured();
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

	protected void addView(final View view) {
		CtrlModel model = getOrCreateComponentModel();
		if (model.views == null) {
			model.views = new ArrayList<>();
		}
		model.views.add(view);
	}

	protected void removeView(final View view) {
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
		resetViewMessages();
		for (View view : getViews()) {
			view.resetView();
		}
	}

	protected void resetViewMessages() {
		getViewMessages().reset();
	}

	protected void handleResetEvent() {
		reset();
	}

	protected boolean isConfigured() {
		return getComponentModel().configured;
	}

	protected void setConfigured() {
		getOrCreateComponentModel().configured = true;
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	protected final void dispatchCtrlEvent(final EventType eventType) {
		dispatchCtrlEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	protected void dispatchCtrlEvent(final EventType eventType, final Object data) {
		dispatchCtrlEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this Controller with the Controller qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	protected void dispatchCtrlEvent(final EventType eventType, final Object data, final Exception exception) {
		Event event = new Event(new EventQualifier(eventType, getQualifier()), data, exception);
		getDispatcher().dispatch(event);
	}

	/**
	 * A helper method to register a listener with an Event Type and the Controller qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 * @return the listener register id
	 */
	protected final String registerCtrlListener(final Listener listener, final EventType eventType) {
		return getDispatcher().register(listener, new EventMatcher(eventType, getQualifier()));
	}

	protected Model getModel(final Class clazz) {
		// Get Parent Combo
		ComboView combo = findParentCombo();
		return combo == null ? null : combo.getModel(clazz);
	}

	protected ComboView findParentCombo() {
		return WebUtilities.getAncestorOfClass(ComboView.class, this);
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

		private boolean configured;

	}

}
