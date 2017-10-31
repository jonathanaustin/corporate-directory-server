package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.EventType;
import com.github.bordertech.flux.View;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;

/**
 *
 * @param <T> the view bean
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface AppView<T> extends AjaxTarget, SubordinateTarget, BeanBound, View {

	/**
	 * Reset the view to the default state.
	 */
	void resetView();

	/**
	 *
	 * @return the view content holder.
	 */
	WComponent getContent();

	/**
	 * Reset the view content.
	 */
	void resetContent();

	/**
	 * @return true if view content is visible
	 */
	boolean isContentVisible();

	/**
	 * @param visible true if make view content visible
	 */
	void setContentVisible(final boolean visible);

	/**
	 * Validate the view. Will dispatch validation errors.
	 *
	 * @return true if valid
	 */
	boolean validateView();

	/**
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

	/**
	 * @return the view bean
	 */
	T getViewBean();

	/**
	 * @param viewBean the view bean
	 */
	void setViewBean(final T viewBean);

	/**
	 * Update the View State onto the Bean.
	 */
	void updateViewBean();

	/**
	 *
	 * @param target the AJAX target to add
	 * @param eventType the event the target is for
	 */
	void addEventAjaxTarget(final AjaxTarget target, final EventType... eventType);

	void registerEventAjaxControl(final EventType type, final AppAjaxControl ajax);

	void clearEventAjaxTargets(final EventType type);

	WMessages getViewMessages();

}
