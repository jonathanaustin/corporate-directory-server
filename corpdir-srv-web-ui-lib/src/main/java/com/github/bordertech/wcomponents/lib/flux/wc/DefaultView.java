package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Controller;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends WDiv implements WView {

	private final Dispatcher dispatcher;

	private final String qualifier;

	private final DefaultViewContent content = new DefaultViewContent() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}

		@Override
		public boolean isVisible() {
			return isContentVisible();
		}
	};

	public DefaultView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultView(final Dispatcher dispatcher, final String qualifier) {
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;
		setSearchAncestors(false);
		setBeanProperty(".");
		add(content);
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
	public final WViewContent getContent() {
		return content;
	}

	@Override
	public WController getController() {
		return getComponentModel().controller;
	}

	@Override
	public void setController(final Controller controller) {
		getOrCreateComponentModel().controller = (WController) controller;
	}

	@Override
	public void resetContent() {
		content.reset();
	}

	@Override
	public boolean isContentVisible() {
		return getComponentModel().contentVisible;
	}

	@Override
	public void setContentVisible(final boolean visible) {
		if (isContentVisible() != visible) {
			getOrCreateComponentModel().contentVisible = visible;
		}
	}

	@Override
	public void resetView() {
		reset();
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !isContentVisible();
	}

	@Override
	public WMessages getViewMessages() {
		return WMessages.getInstance(this);
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		// Do Nothing
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 */
	protected void dispatchViewEvent(final EventType eventType) {
		dispatchViewEvent(eventType, null, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 */
	protected void dispatchViewEvent(final EventType eventType, final Object data) {
		dispatchViewEvent(eventType, data, null);
	}

	/**
	 * Helper method to dispatch an event for this view with the view qualifier automatically added.
	 *
	 * @param eventType the event type
	 * @param data the event data
	 * @param exception an exception
	 */
	protected void dispatchViewEvent(final EventType eventType, final Object data, final Exception exception) {
		Event event = new Event(this, new EventQualifier(eventType, getQualifier()), data, exception);
		getDispatcher().dispatch(event);
	}

	/**
	 * Helper method to add target to an Ajax Control.
	 *
	 * @param ajax the AJAX control
	 * @param target the AJAX target
	 */
	protected void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final AjaxTarget target) {
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
		WController ctrl = getController();
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

		private WController controller;

		private boolean contentVisible = true;
	}

}
