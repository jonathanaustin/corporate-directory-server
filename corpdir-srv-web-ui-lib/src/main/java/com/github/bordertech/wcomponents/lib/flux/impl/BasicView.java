package com.github.bordertech.wcomponents.lib.flux.impl;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.WDiv;
import com.github.bordertech.wcomponents.lib.flux.EventType;
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
	WDiv getContent();

	/**
	 * Reset the view content.
	 */
	void resetContent();

	/**
	 * Validate the view. Will report errors in the View Messages.
	 *
	 * @return true if valid
	 */
	boolean validateView();

	/**
	 * Make the view holder visible.
	 */
	void makeContentVisible();

	/**
	 * Make the view holder invisible.
	 */
	void makeContentInvisible();

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

	/**
	 *
	 * @param target the AJAX target to add
	 * @param eventType the event the target is for
	 */
	void addEventTarget(final AjaxTarget target, final EventType... eventType);

}
