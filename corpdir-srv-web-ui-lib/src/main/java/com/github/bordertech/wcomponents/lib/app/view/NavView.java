package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.BasicEventView;
import com.github.bordertech.wcomponents.lib.view.ViewAction;

/**
 * Entity action menu.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface NavView extends BasicEventView {

	void setPosition(final int idx, final int size);

	int getCurrentIdx();

	int getSize();

	public void registerViewAction(final NavEvent viewEvent, final ViewAction<NavView, NavEvent> viewAction);
}
