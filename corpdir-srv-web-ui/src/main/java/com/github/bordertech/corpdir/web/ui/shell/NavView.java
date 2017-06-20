package com.github.bordertech.corpdir.web.ui.shell;

/**
 * Entity action menu.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface NavView extends BasicEventView {

	void addNavAction(final NavEvent event, final NavAction action);

	void setPosition(final int idx, final int size);

	int getCurrentIdx();

	int getSize();
}
