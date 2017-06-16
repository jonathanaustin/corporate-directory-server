package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.Action;

/**
 * Entity action menu.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface NavMenuView extends BasicView {

	void setPosition(final int idx, final int size);

	int getCurrentIdx();

	int getSize();

	void setNavAction(final NavEvent event, final Action action);

	Action getNavAction(final NavEvent event);

}
