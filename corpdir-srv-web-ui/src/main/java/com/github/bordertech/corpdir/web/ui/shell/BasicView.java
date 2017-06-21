package com.github.bordertech.corpdir.web.ui.shell;

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
	WContainer getContent();

	/**
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(boolean searchAncestors);

}
