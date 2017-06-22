package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.view.ViewEvent;

/**
 * Navigation events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface NavEvent extends ViewEvent {

	NavEvent FIRST = new NavEvent() {
	};
	NavEvent PREV = new NavEvent() {
	};
	NavEvent NEXT = new NavEvent() {
	};
	NavEvent LAST = new NavEvent() {
	};
}
