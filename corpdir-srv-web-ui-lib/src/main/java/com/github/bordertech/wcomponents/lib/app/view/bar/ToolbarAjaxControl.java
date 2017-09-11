package com.github.bordertech.wcomponents.lib.app.view.bar;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.AjaxTrigger;
import com.github.bordertech.wcomponents.WAjaxControl;

/**
 *
 * @author jonathan
 */
public class ToolbarAjaxControl extends WAjaxControl {

	public ToolbarAjaxControl(final AjaxTrigger trigger, final AjaxTarget target) {
		super(trigger, target);
	}

	@Override
	public boolean isVisible() {
		return getTrigger().isVisible();
	}

}
