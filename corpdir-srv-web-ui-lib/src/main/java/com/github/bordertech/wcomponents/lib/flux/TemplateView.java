package com.github.bordertech.wcomponents.lib.flux;

import com.github.bordertech.wcomponents.WTemplate;

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
		getViewHolder().add(template);
	}

	public final WTemplate getViewTemplate() {
		return template;
	}

}
