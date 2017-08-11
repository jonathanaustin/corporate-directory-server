package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.lib.flux.impl.DefaultView;
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
		super(dispatcher);
		getHolder().add(template);
	}

	public final WTemplate getViewTemplate() {
		return template;
	}

}
