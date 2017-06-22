package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;

/**
 * Default basic view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultBasicView extends WPanel implements BasicView {

	private final WContainer holder = new WContainer() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	@Override
	public final WContainer getViewContent() {
		return holder;
	}

	protected void initViewContent(final Request request) {
		// Do nothing
	}

}
