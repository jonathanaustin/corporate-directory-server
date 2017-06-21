package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WPanel;

/**
 * Abstract basic view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class AbstractBasicView extends WPanel implements BasicView {

	private final WContainer holder = new WContainer() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initContent(request);
				setInitialised(true);
			}
		}
	};

	@Override
	public final WContainer getContent() {
		return holder;
	}

	protected void initContent(final Request request) {

	}

}
