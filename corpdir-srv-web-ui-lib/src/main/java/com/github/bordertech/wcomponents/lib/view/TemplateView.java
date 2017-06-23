package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.WTemplate;

/**
 * View will use a Template to do layout.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface TemplateView extends BasicView {

	/**
	 *
	 * @return the template for the view.
	 */
	WTemplate getViewTemplate();
}
