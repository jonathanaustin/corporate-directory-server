package com.github.bordertech.wcomponents.lib.app.view;

import com.github.bordertech.wcomponents.lib.pub.Event;

/**
 * List events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ListEvent extends Event {

	ListEvent VIEW = new ListEvent() {
	};
	ListEvent EDIT = new ListEvent() {
	};
	ListEvent DELETE = new ListEvent() {
	};
	ListEvent SELECT = new ListEvent() {
	};
}
