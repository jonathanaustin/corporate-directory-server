package com.github.bordertech.wcomponents.lib.view;

import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.List;

/**
 * Basic View with Events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicEventView extends BasicView {

	List<ViewAction> getRegisteredViewActions(final ViewEvent viewEvent);

	boolean hasRegisteredViewAction(final ViewEvent viewEvent);

	/**
	 *
	 * @param target the AJAX target to add to any actions in the view
	 * @param viewEvents the specific view events (or not specified)
	 */
	void addEventAjaxTarget(final AjaxTarget target, final ViewEvent... viewEvents);

}
