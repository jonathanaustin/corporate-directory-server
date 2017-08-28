package com.github.bordertech.wcomponents.lib.mvc;

import com.github.bordertech.wcomponents.BeanBound;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ViewBound<T> extends View, BeanBound {

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

}
