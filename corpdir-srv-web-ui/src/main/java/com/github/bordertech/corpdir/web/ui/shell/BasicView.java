package com.github.bordertech.corpdir.web.ui.shell;

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
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(boolean searchAncestors);

}
