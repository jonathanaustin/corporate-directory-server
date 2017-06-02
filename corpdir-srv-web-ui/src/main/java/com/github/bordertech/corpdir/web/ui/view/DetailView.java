package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.api.common.ApiKeyIdObject;
import com.github.bordertech.wcomponents.WComponent;

/**
 *
 * @param <T> the API object type
 * @author jonathan
 *
 */
public interface DetailView<T extends ApiKeyIdObject> extends WComponent {

	/**
	 * @return the bean being displayed.
	 */
	T getApiBean();

	/**
	 * @param apiBean the bean to be displayed
	 */
	void setApiBean(final T apiBean);

	/**
	 * @return true if form read only
	 */
	boolean isFormReadOnly();

	/**
	 *
	 * @param formReadOnly true if form read only
	 */
	void setFormReadOnly(final boolean formReadOnly);

}
