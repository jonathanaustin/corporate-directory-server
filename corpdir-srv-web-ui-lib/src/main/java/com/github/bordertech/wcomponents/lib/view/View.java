package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.lib.pub.Publisher;
import com.github.bordertech.wcomponents.lib.pub.Subscriber;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface View extends Publisher, Subscriber, AjaxTarget, SubordinateTarget, BeanBound {

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
