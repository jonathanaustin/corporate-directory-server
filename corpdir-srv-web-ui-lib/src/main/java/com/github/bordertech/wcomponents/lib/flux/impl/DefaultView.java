package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends WDiv implements BasicView {

	private final Dispatcher dispatcher;

	private final String qualifier;

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

	public DefaultView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public DefaultView(final Dispatcher dispatcher, final String qualifier) {
		this.dispatcher = dispatcher;
		this.qualifier = qualifier;
	}

	@Override
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public String getQualifier() {
		return qualifier;
	}

	@Override
	public void addEventTarget(final AjaxTarget target, final EventType... eventType) {
		// Do Nothing
	}

	@Override
	public final WDiv getViewHolder() {
		return holder;
	}

	protected void initViewContent(final Request request) {
		// Do nothing
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
