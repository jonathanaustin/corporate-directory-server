package com.github.bordertech.wcomponents.lib.flux;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.lib.WDiv;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends WDiv implements View {

	private final Dispatcher dispatcher;

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
		this.dispatcher = dispatcher;
	}

	@Override
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public void addEventTarget(final String eventType, final AjaxTarget target) {
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

	public static class ViewModel extends DivModel {
	}

}
