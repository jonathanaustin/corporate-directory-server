package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import java.util.List;

/**
 * Basic View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicView extends AjaxTarget, SubordinateTarget, BeanBound {

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

	/**
	 *
	 * @param viewEvent the event
	 * @return the registered actions for this event
	 */
	List<ViewAction> getRegisteredViewActions(final ViewEvent viewEvent);

	/**
	 *
	 * @param viewEvent the view event
	 * @return true if view has a registered action for this event
	 */
	boolean hasRegisteredViewAction(final ViewEvent viewEvent);

	/**
	 *
	 * @param target the AJAX target to add to any actions in the view
	 * @param viewEvents the specific view events (or not specified)
	 */
	void addEventAjaxTarget(final AjaxTarget target, final ViewEvent... viewEvents);

}
