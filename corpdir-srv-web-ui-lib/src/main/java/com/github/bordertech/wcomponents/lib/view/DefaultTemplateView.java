package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.WTemplate;

/**
 * AbstraDefault template view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultTemplateView extends DefaultBasicEventView implements TemplateView {

	private final WTemplate template = new WTemplate();

	public DefaultTemplateView() {
		getViewHolder().add(template);
	}

	@Override
	public final WTemplate getViewTemplate() {
		return template;
	}

}
