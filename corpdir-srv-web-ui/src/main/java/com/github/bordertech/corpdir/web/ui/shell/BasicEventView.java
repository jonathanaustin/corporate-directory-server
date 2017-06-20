package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.List;

/**
 * Basic View with Events.
 *
 * @author Jonathan Austin
 * @param <E> the event type
 * @param <V> the action type
 * @since 1.0.0
 */
public interface BasicEventView<V extends BasicEventView, E extends ViewEvent> extends BasicView {

	List<ViewAction> getViewActions();

	List<ViewAction> getViewActions(final E viewEvent);

	void addViewAction(final E viewEvent, final ViewAction<V, E> viewAction);

	/**
	 *
	 * @param target the AJAX target to add to any actions in the view
	 */
	void addAjaxTarget(final AjaxTarget target);

}
