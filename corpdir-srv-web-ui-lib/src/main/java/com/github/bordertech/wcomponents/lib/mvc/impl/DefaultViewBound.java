package com.github.bordertech.wcomponents.lib.mvc.impl;

import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.lib.mvc.ViewBound;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 * @param <T> the view bean
 */
public class DefaultViewBound<T> extends DefaultView implements ViewBound<T> {

	public DefaultViewBound() {
		setSearchAncestors(false);
		setBeanProperty(".");
	}

	@Override
	public void updateViewBean() {
		WebUtilities.updateBeanValue(this);
	}

	@Override
	public T getViewBean() {
		return (T) getBean();
	}

	@Override
	public void setViewBean(final T viewBean) {
		setBean(viewBean);
	}
}
