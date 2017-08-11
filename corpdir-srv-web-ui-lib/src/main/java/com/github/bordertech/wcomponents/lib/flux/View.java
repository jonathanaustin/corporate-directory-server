package com.github.bordertech.wcomponents.lib.flux;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.lib.WDiv;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface View extends AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return the view content holder.
	 */
	WDiv getViewHolder();

	/**
	 *
	 * @return the dispatcher for this view.
	 */
	Dispatcher getDispatcher();

	/**
	 * @return the view qualifier (if needed)
	 */
	String getQualifier();

	/**
	 * @param target the AJAX target for an event type
	 * @param eventType the eventType to add AJAX target to, can be null to add to ALL.
	 */
	void addEventTarget(final AjaxTarget target, final EventType... eventType);

	/**
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

}
