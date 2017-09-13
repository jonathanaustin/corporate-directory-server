package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.app.common.AppAjaxControl;
import com.github.bordertech.wcomponents.lib.flux.EventType;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 * @param <T> the view bean
 */
public interface View<T> extends BaseMvc, AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return true if view is a naming context
	 */
	boolean isQualifierContext();

	/**
	 *
	 * @param context true if view is a naming context
	 */
	void setQualifierContext(final boolean context);

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

}
