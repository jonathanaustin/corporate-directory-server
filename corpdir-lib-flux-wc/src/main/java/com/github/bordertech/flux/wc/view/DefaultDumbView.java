package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Default dumb view.
 * <p>
 * Uses a {@link WContainer} to hold the content.
 * </p>
 *
 * @param <T> the view bean type
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDumbView<T> extends AbstractDumbView<T> {

	private final WContainer content = new WContainer() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	public DefaultDumbView(final String viewId) {
		super(viewId);
		addTaggedComponent(TemplateConstants.TAG_VW_CONTENT, content);
	}

	@Override
	public final WContainer getContent() {
		return content;
	}

}
