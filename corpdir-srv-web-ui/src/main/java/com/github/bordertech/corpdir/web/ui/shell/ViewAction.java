package com.github.bordertech.corpdir.web.ui.shell;

import java.io.Serializable;

/**
 * View Action.
 *
 * @author jonathan
 * @param <V> the view
 * @param <E> the view event type
 */
public interface ViewAction<V extends BasicEventView, E extends ViewEvent> extends Serializable {

	/**
	 *
	 * @param view the view triggering event
	 * @param viewEvent the view event
	 */
	void execute(final V view, final E viewEvent);

}
