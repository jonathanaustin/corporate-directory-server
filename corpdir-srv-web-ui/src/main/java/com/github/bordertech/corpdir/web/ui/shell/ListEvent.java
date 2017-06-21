package com.github.bordertech.corpdir.web.ui.shell;

/**
 * List events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface ListEvent extends ViewEvent {

	ListEvent VIEW = new ListEvent() {
	};
	ListEvent EDIT = new ListEvent() {
	};
	ListEvent DELETE = new ListEvent() {
	};
	ListEvent SELECT = new ListEvent() {
	};
}
