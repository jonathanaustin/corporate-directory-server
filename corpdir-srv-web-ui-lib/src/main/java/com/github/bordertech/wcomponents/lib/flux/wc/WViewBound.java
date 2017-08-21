package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.lib.flux.ViewBound;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface WViewBound<T> extends WView, BeanBound, ViewBound<T> {

	/**
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

}
