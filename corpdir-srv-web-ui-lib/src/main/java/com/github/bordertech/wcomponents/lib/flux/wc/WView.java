package com.github.bordertech.wcomponents.lib.flux.wc;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.lib.flux.EventType;
import com.github.bordertech.wcomponents.lib.flux.View;

/**
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface WView extends View, AjaxTarget, SubordinateTarget, WComponent {

	/**
	 *
	 * @return the view content holder.
	 */
	@Override
	WViewContent getContent();

	/**
	 *
	 * @return the messages component for this view
	 */
	WMessages getViewMessages();

	/**
	 *
	 * @return the controller attached to this view
	 */
	@Override
	WController getController();

	/**
	 *
	 * @param target the AJAX target to add
	 * @param eventType the event the target is for
	 */
	void addEventTarget(final AjaxTarget target, final EventType... eventType);

}
