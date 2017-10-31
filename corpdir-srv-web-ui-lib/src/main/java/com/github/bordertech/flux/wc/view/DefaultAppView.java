package com.github.bordertech.flux.wc.view;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Default view.
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultAppView<T> extends AbstractAppView<T> {

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

	public DefaultAppView() {
		addTaggedComponent("vw-content", content);
	}

	@Override
	public final WContainer getContent() {
		return content;
	}

}
