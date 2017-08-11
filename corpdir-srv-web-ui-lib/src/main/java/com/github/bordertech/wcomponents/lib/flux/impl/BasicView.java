package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.View;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicView extends View, AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return the view content holder.
	 */
	WDiv getViewHolder();

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
