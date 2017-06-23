package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.Request;

/**
 * Default basic view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultBasicView extends WDiv implements BasicView {

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

	@Override
	public final WDiv getViewHolder() {
		return holder;
	}

	protected void initViewContent(final Request request) {
		// Do nothing
	}

}
