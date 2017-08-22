package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.lib.flux.Dispatcher;

/**
 * AbstraDefault template view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class TemplateView extends DefaultView {

	private final WTemplate template = new WTemplate();

	public TemplateView(final Dispatcher dispatcher) {
		this(dispatcher, null);
	}

	public TemplateView(final Dispatcher dispatcher, final String qualifier) {
		super(dispatcher, qualifier);
		getContent().add(template);
	}

	public final WTemplate getViewTemplate() {
		return template;
	}

}
