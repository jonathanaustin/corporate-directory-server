package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.BasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;

/**
 * Navigation menu.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface NavView extends BasicEventView {

	int getCurrentIdx();

	void setCurrentIdx(final int idx);

	int getSize();

	void setSize(final int size);

	boolean isUseSwipe();

	void setUseSwipe(final boolean useSwipe);

	public void registerViewAction(final ViewAction<NavView, NavEvent> viewAction, final NavEvent... viewEvent);
}
