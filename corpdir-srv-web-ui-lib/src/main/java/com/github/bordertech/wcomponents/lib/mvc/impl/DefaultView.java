package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultView extends AbstractView {

	private final WContainer content = new WContainer() {
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
		super(dispatcher, qualifier);
		addTaggedComponent("vw-content", content);
	}

	@Override
	public final WContainer getContent() {
		return content;
	}

}
