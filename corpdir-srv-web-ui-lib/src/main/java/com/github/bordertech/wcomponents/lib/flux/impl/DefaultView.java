package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Controller;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import com.github.bordertech.wcomponents.validation.WValidationErrors;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the view bean
 */
public class DefaultView<T> extends WDiv implements BasicView<T> {

	private final Dispatcher dispatcher;

	private final String qualifier;

	private final WDiv viewHolder = new WDiv() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	public DefaultView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultView(final Dispatcher dispatcher, final String qualifier) {
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;

		add(viewHolder);

		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public final Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public final BasicController getController() {
		return getComponentModel().controller;
	}

	@Override
	public void setController(final Controller controller) {
		getOrCreateComponentModel().controller = (BasicController) controller;
	}

	@Override
	public final String getQualifier() {
		return qualifier;
	}

	@Override
	public final WDiv getViewHolder() {
		return viewHolder;
	}

	@Override
	public void resetHolder() {
		viewHolder.reset();
	}

	@Override
	public final void makeHolderVisible() {
		viewHolder.setVisible(true);
	}

	@Override
	public final void makeHolderInvisible() {
		viewHolder.setVisible(false);
	}

	@Override
	public void updateViewBean() {
		WebUtilities.updateBeanValue(this);
	}

	@Override
	public boolean validateView() {
		WValidationErrors errorsBox = getViewMessages().getValidationErrors();
		errorsBox.clearErrors();

		List<Diagnostic> diags = new ArrayList<>();
		viewHolder.validate(diags);
		viewHolder.showWarningIndicators(diags);
		viewHolder.showErrorIndicators(diags);

		if (containsError(diags)) {
			errorsBox.setErrors(diags);
			errorsBox.setFocussed();
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !viewHolder.isVisible();
	}

	@Override
	public final WMessages getViewMessages() {
		return WMessages.getInstance(this);
	}

	@Override
	public final T getViewBean() {
		return (T) getBean();
	}

	@Override
	public final void setViewBean(final T viewBean) {
		setBean(viewBean);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	public final void dispatchViewEvent(final EventType eventType) {
		dispatchViewEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	public void dispatchViewEvent(final EventType eventType, final Object data) {
		dispatchViewEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	public void dispatchViewEvent(final EventType eventType, final Object data, final Exception exception) {
		Event event = new Event(this, new EventQualifier(eventType, getQualifier()), data, exception);
		getDispatcher().dispatch(event);
	}

	/**
	 * A helper method to register a listener with an Event Type and the View qualifier automatically added.
	 *
	 * @param listener
	 * @param eventType
	 * @return the listener register id
	 */
	public final String registerViewListener(final Listener listener, final EventType eventType) {
		return getDispatcher().register(listener, new EventMatcher(eventType, getQualifier()));
	}

	/**
	 * Update the view bean.
	 */
	public void doUpdateViewBean() {
		WebUtilities.updateBeanValue(getViewHolder());
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		// Do Nothing
	}

	/**
	 * Helper method to add target to an Ajax Control.
	 *
	 * @param ajax the AJAX control
	 * @param target the AJAX target
	 */
	public void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final AjaxTarget target) {
		if (target == null) {
			return;
		}
		// Make Sure the Targets have not already been added
		List<AjaxTarget> current = ajax.getTargets();
		if (!current.contains(target)) {
			ajax.addTarget(target);
		}
	}

	protected void initViewContent(final Request request) {
		BasicController ctrl = getController();
		if (ctrl != null) {
			getController().configAjax(this);
		}
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Just here as a place holder and easier for other Views to extend.
	 */
	public static class ViewModel extends DivModel {

		private BasicController controller;
	}

	/**
	 * Borrowed from ValidatinAction (Should be public). Indicates whether the given list of diagnostics contains any
	 * errors.
	 *
	 * @param diags the list into which any validation diagnostics were added.
	 * @return true if any of the diagnostics in the list are errors, false otherwise.
	 */
	private static boolean containsError(final List<Diagnostic> diags) {
		if (diags == null || diags.isEmpty()) {
			return false;
		}

		for (Diagnostic diag : diags) {
			if (diag.getSeverity() == Diagnostic.ERROR) {
				return true;
			}
		}

		return false;
	}

}
