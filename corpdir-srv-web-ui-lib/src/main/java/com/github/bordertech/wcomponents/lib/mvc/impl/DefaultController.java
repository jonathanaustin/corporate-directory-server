package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
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
import com.github.bordertech.wcomponents.lib.mvc.ViewCombo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	public final String registerCtrlListener(final Listener listener, final EventType eventType) {
		return getDispatcher().register(listener, new EventMatcher(eventType, getQualifier()));
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isConfigured()) {
			throw new IllegalStateException("configViews has not been called on the controller");
		}
	}

	@Override
	public void configViews() {
		checkConfig();
		// Call Config on any COMBO Views
		for (View view : getViews()) {
			if (view instanceof ViewCombo) {
				((ViewCombo) view).configComboViews();
			}
		}
		setConfigured();
	}

	protected boolean isConfigured() {
		return getComponentModel().configured;
	}

	protected void setConfigured() {
		getOrCreateComponentModel().configured = true;
	}

	protected void checkConfig() {
	}

	@Override
	public void configAjax(final View view) {
		// By default ADD all the views as AJAX
		for (View vw : getViews()) {
			view.addEventTarget(vw);
		}
		// Get the parent controller AJAX Targets
		Controller parent = findParentCtrl();
		if (parent != null) {
			parent.configAjax(view);
		}
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
	public void addView(final View view) {
		view.setController(this);
		CtrlModel model = getOrCreateComponentModel();
		if (model.views == null) {
			model.views = new ArrayList<>();
		}
		model.views.add(view);
	}

	@Override
	public void removeView(final View view) {
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

	protected void handleResetEvent() {
		reset();
	}

	@Override
	public void addModel(final Model model) {
		CtrlModel ctrl = getOrCreateComponentModel();
		if (ctrl.models == null) {
			ctrl.models = new ArrayList<>();
		}
		ctrl.models.add(model);
	}

	@Override
	public Model getModel(final Class clazz) {
		for (Model model : getModels()) {
			if (clazz.isAssignableFrom(model.getClass())) {
				return model;
			}
		}
		// Not found, so try the parent controller Models
		Controller parent = findParentCtrl();
		return parent == null ? null : parent.getModel(clazz);
	}

	protected List<Model> getModels() {
		CtrlModel ctrl = getComponentModel();
		return ctrl.models == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(ctrl.models);
	}

	protected Controller findParentCtrl() {
		if (isBlocking()) {
			return null;
		}
		ViewCombo combo = WebUtilities.getAncestorOfClass(ViewCombo.class, this);
		return combo == null ? null : combo.getController();

	}

	@Override
	public boolean isBlocking() {
		return getComponentModel().block;
	}

	@Override
	public void setBlocking(final boolean block) {
		getOrCreateComponentModel().block = block;
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

		private List<Model> models;

		private boolean block;
	}

	@Override
	protected void assertAddSupported(final WComponent componentToAdd) {
		throw new IllegalStateException("Controllers cannot have any child components");
	}

}
