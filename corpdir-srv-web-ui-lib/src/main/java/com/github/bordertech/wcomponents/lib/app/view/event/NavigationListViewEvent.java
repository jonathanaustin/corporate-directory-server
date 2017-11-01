package com.github.bordertech.wcomponents.lib.app.view.event;

import com.github.bordertech.flux.event.ViewEventType;

/**
 * Navigation for list items.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum NavigationListViewEvent implements ViewEventType {
	FIRST,
	PREV,
	NEXT,
	LAST
}
