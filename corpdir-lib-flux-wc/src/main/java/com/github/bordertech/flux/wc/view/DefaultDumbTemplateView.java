package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.wc.common.TemplateConstants;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;

/**
 * Default dumb template View.
 * <p>
 * Uses a {@link WTemplate} to hold the content.
 * </p>
 *
 * @param <T> the view bean type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultDumbTemplateView<T> extends AbstractDumbView<T> {

	private final WTemplate content = new WTemplate() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				initViewContent(request);
				setInitialised(true);
			}
		}
	};

	public DefaultDumbTemplateView(final String viewId, final String templateName) {
		super(viewId);
		content.setTemplateName(templateName);
		content.setEngineName(TemplateRendererFactory.TemplateEngine.HANDLEBARS);
		addTaggedComponent(TemplateConstants.TAG_VW_CONTENT, content);
	}

	@Override
	public final WTemplate getContent() {
		return content;
	}

	/**
	 * Helper method to add a component to the template.
	 *
	 * @param tag the component identifying tag
	 * @param component the component to add to the template
	 */
	public void addComponentToTemplate(final String tag, final WComponent component) {
		getContent().addTaggedComponent(tag, component);
	}

}
