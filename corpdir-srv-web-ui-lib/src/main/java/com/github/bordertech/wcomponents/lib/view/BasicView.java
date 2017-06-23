package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.WComponent;

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
	WDiv getViewHolder();

	/**
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

}
