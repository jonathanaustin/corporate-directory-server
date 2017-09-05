package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;

/**
 * AbstraDefault template view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TemplateView extends AbstractView {

	private final WTemplate content = new WTemplate() {
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

	public TemplateView(final String templateName) {
		this(templateName, null);
	}

	public TemplateView(final String templateName, final String qualifier) {
		super(qualifier);
		content.setTemplateName(templateName);
		content.setEngineName(TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		addTaggedComponent("vw-content", content);
	}

	@Override
	public final WTemplate getContent() {
		return content;
	}

}
