package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.AjaxTarget;
import java.util.List;

/**
 * Basic View with Events.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface BasicEventView extends BasicView {

	List<ViewAction> getViewActions(final ViewEvent viewEvent);

	/**
	 *
	 * @param target the AJAX target to add to any actions in the view
	 * @param viewEvent the specific view events (or not specified)
	 */
	void addAjaxTarget(final AjaxTarget target, final ViewEvent... viewEvent);

}
