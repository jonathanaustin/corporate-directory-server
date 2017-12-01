package com.github.bordertech.flux.wc.view;

import com.github.bordertech.flux.view.DumbView;
import com.github.bordertech.flux.view.ViewEventType;
import com.github.bordertech.flux.wc.common.AppAjaxControl;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.BeanBound;
import com.github.bordertech.wcomponents.SubordinateTarget;
import com.github.bordertech.wcomponents.WComponent;

/**
 * View with WComponent requirements.
 *
 * @param <T> the view bean
 * @author Jonathan Austin
 * @since 1.0.0
 */
public interface FluxDumbView<T> extends DumbView<T>, AjaxTarget, SubordinateTarget, BeanBound {

	/**
	 *
	 * @return the view content holder.
	 */
	WComponent getContent();

	/**
	 * This method is here until it is added to BeanBound.
	 *
	 * @param searchAncestors true if search ancestors.
	 */
	void setSearchAncestors(final boolean searchAncestors);

	/**
	 *
	 * @param target the AJAX target to add
	 * @param eventType the event the target is for
	 */
	void addEventAjaxTarget(final AjaxTarget target, final ViewEventType... eventType);

	/**
	 * Register an Ajax Control for an event type.
	 *
	 * @param type the event type
	 * @param ajax the AJAX control
	 */
	void registerEventAjaxControl(final ViewEventType type, final AppAjaxControl ajax);

	/**
	 * Clear the AJAX targets for an event type.
	 *
	 * @param type
	 */
	void clearEventAjaxTargets(final ViewEventType type);

}
