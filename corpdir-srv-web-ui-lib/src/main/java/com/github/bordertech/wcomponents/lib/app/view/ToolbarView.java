package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Toolbar view.
 *
 * @author Jonathan Austin
 * @param <T> the view bean type
 * @since 1.0.0
 *
 */
public interface ToolbarView<T> extends View<T> {

	boolean isUseBack();

	void setUseBack(final boolean useBack);

	boolean isUseAdd();

	void setUseAdd(final boolean useAdd);

	boolean isUseReset();

	void setUseReset(final boolean useReset);

}
