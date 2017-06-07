package com.github.bordertech.corpdir.web.ui.shell;

import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.WComponent;

/**
 * Action Bar for the Action View.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 *
 */
public interface ActionBar extends WComponent {

	void addAjaxTarget(final AjaxTarget target);

}
