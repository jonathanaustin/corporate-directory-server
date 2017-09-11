package com.github.bordertech.wcomponents.lib.app.common;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WAjaxControl;

/**
 * Custom WAjaxControl to only be visible if its trigger is visible.
 *
 * @author jonathan
 */
public class AppAjaxControl extends WAjaxControl {

	public AppAjaxControl(final AjaxTrigger trigger) {
		super(trigger);
	}

	public AppAjaxControl(final AjaxTrigger trigger, final AjaxTarget target) {
		super(trigger, target);
	}

	@Override
	public boolean isVisible() {
		return getTrigger() == null || getTrigger().isVisible();
	}

	@Override
	public void addTarget(final AjaxTarget target) {
		if (target != null && !getTargets().contains(target)) {
			super.addTarget(target);
		}
	}

}
