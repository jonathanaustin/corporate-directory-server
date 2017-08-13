package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AbstractWComponent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.ComponentModel;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.Controller;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.View;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultController extends AbstractWComponent implements Controller {

	private final Dispatcher dispatcher;

	public DefaultController(final Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public final Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public final WMessages getViewMessages() {
		return WMessages.getInstance(this);
	}

	@Override
	public List<AjaxTarget> getEventTargets(final View view, final EventType eventType) {
		return Collections.EMPTY_LIST;
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
