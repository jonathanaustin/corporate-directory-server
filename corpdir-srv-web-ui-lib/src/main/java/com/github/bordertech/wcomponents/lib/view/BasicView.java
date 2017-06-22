package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;

/**
 * Basic View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicView extends WComponent, BeanBound {

	/**
	 *
	 * @return the view content holder.
	 */
	WContainer getViewContent();

	/**
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

}
