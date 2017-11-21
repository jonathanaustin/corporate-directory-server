package com.github.bordertech.flux.view;

import java.io.Serializable;

/**
 * View.
 *
 * @param <T> the view bean
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface DumbView<T> extends Serializable {

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
	 * Dispatches an event to the parent container view.
	 *
	 * @param eventType the view event
	 */
	void dispatchViewEvent(final ViewEventType eventType);

	/**
	 * Dispatches an event to the parent container view.
	 *
	 * @param eventType the view event
	 * @param data the data
	 */
	void dispatchViewEvent(final ViewEventType eventType, final Object data);

}
