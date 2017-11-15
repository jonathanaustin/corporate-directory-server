package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.event.ViewEventType;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.flux.wc.app.common.AppAjaxControl;

/**
 * View with WComponent requirements.
 *
 * @param <T> the view bean
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DumbView<T> extends AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return the view unique identifier
	 */
	String getViewId();

	/**
	 * Reset the view to the default state.
	 */
	void resetView();

	/**
	 * Validate the view. Will dispatch validation errors.
	 *
	 * @return true if valid
	 */
	boolean validateView();

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
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

	/**
	 * Update the View State onto the Bean.
	 */
	void updateViewBean();

	/**
	 * @return the view bean
	 */
	T getViewBean();

	/**
	 * @param viewBean the view bean
	 */
	void setViewBean(final T viewBean);

	/**
	 *
	 * @param target the AJAX target to add
	 * @param eventType the event the target is for
	 */
	void addEventAjaxTarget(final AjaxTarget target, final ViewEventType... eventType);

	/**
	 * Register an Ajax Control for an event type.
	 *
	 * @param type the event type
	 * @param ajax the AJAX control
	 */
	void registerEventAjaxControl(final ViewEventType type, final AppAjaxControl ajax);

	/**
	 * Clear the AJAX targets for an event type.
	 *
	 * @param type
	 */
	void clearEventAjaxTargets(final ViewEventType type);

	/**
	 * Dispatches an event to the parent container view.
	 *
	 * @param eventType the view event
	 * @param data the data
	 */
	void dispatchViewEvent(final ViewEventType eventType, final Object data);

}
