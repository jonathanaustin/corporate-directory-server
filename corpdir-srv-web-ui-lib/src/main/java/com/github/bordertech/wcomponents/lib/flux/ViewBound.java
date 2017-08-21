package com.github.bordertech.wcomponents.lib.flux;

/**
 * A view bound to a bean.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the View bean type
 */
public interface ViewBound<T> extends View {

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
	 * Validate the view. Will report errors in the View Messages.
	 *
	 * @return true if valid
	 */
	boolean validateView();

}
