package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Entity action view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ToolbarView extends View {

	boolean isUseBack();

	void setUseBack(final boolean useBack);

	boolean isUseAdd();

	void setUseAdd(final boolean useAdd);

	boolean isUseReset();

	void setUseReset(final boolean useReset);

}
