package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.Controller;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;
import java.util.List;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends WDiv implements BasicView {

	private final Controller ctrl;

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

	public DefaultView(final Controller ctrl) {
		this(ctrl, null);
	}

	public DefaultView(final Controller ctrl, final String qualifier) {
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
	public Controller getController() {
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

	public static final void addEventTargetsToAjaxCtrl(final WAjaxControl ajax, final List<AjaxTarget> targets) {
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
