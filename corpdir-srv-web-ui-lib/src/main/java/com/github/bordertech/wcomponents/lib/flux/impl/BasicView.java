package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.View;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 * @param <T> the view bean
 */
public interface BasicView<T> extends View<T>, AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return the view content holder.
	 */
	WDiv getViewHolder();

	/**
	 * Reset the view holder.
	 */
	void resetHolder();

	/**
	 * Validate the view.Will report errors in the View Messages.
	 *
	 * @return true if valid
	 */
	boolean validateView();

	/**
	 * Make the view holder visible.
	 */
	void makeHolderVisible();

	/**
	 * Make the view holder invisible.
	 */
	void makeHolderInvisible();

	/**
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

	/**
	 *
	 * @return the messages component for this view
	 */
	WMessages getViewMessages();

	/**
	 *
	 * @return the controller attached to this view
	 */
	@Override
	BasicController getController();

}
