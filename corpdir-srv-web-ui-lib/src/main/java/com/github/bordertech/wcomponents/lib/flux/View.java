package com.github.bordertech.wcomponents.lib.flux;

import java.io.Serializable;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the View bean type
 */
public interface View<T> extends Serializable {

	/**
	 *
	 * @return the dispatcher attached to this view.
	 */
	Dispatcher getDispatcher();

	/**
	 * @return the view qualifier (if needed)
	 */
	String getQualifier();

	/**
	 *
	 * @return the view controller
	 */
	Controller getController();

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

}
