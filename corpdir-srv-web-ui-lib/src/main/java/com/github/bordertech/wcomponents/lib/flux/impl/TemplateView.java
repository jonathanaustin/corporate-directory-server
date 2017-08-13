package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.flux.Controller;

/**
 * AbstraDefault template view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TemplateView extends DefaultView {

	private final WTemplate template = new WTemplate();

	public TemplateView(final Controller ctrl) {
		this(ctrl, null);
	}

	public TemplateView(final Controller ctrl, final String qualifier) {
		super(ctrl, qualifier);
		getViewHolder().add(template);
	}

	public final WTemplate getViewTemplate() {
		return template;
	}

}
