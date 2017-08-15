package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.Event;
import com.github.bordertech.wcomponents.lib.flux.EventMatcher;
import com.github.bordertech.wcomponents.lib.flux.EventQualifier;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.Listener;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the view bean
 */
public class DefaultView<T> extends WDiv implements BasicView<T> {

	private final BasicController ctrl;

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

	public DefaultView(final BasicController ctrl) {
		this(ctrl, null);
	}

	public DefaultView(final BasicController ctrl, final String qualifier) {
		this.ctrl = ctrl;
		this.qualifier = qualifier;

		add(viewHolder);

		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public final Dispatcher getDispatcher() {
		return ctrl.getDispatcher();
	}

	@Override
	public BasicController getController() {
		return ctrl;
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
	public void makeHolderVisible() {
		viewHolder.setVisible(true);
	}

	@Override
	public void makeHolderInvisible() {
		viewHolder.setVisible(false);
	}

	protected void initViewContent(final Request request) {
		// Do nothing
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
	public T getViewBean() {
		return (T) getBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		setBean(viewBean);
	}

	@Override
	public void dispatchViewEvent(final EventType eventType) {
		dispatchViewEvent(eventType, null);
	}

	@Override
	public void dispatchViewEvent(final EventType eventType, final Object data) {
		Event event = new Event(this, new EventQualifier(eventType, getQualifier()), data);
		getDispatcher().dispatch(event);
	}

	@Override
	public String registerViewListener(final Listener listener, final EventType eventType) {
		return getDispatcher().register(listener, new EventMatcher(eventType, getQualifier()));
	}

	/**
	 * Update the view bean.
	 */
	public void doUpdateViewBean() {
		WebUtilities.updateBeanValue(getViewHolder());
	}

	/**
	 * Helper method to add targets to an Ajax Control.
	 *
	 * @param ajax the AJAX control
	 * @param targets the AJAX targets
	 */
	public void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final List<AjaxTarget> targets) {
		if (targets == null || targets.isEmpty()) {
			return;
		}
		// Make Sure the Targets have not already been added
		List<AjaxTarget> current = ajax.getTargets();
		for (AjaxTarget target : targets) {
			if (!current.contains(target)) {
				ajax.addTarget(target);
			}
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
	}

}
