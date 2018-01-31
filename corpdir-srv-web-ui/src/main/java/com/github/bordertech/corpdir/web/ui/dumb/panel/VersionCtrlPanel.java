package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiIdPanel;

/**
 * Version Ctrl Detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class VersionCtrlPanel extends BasicApiIdPanel<VersionCtrl> {

	/**
	 * Construct basic panel.
	 *
	 * @param viewId the viewId
	 */
	public VersionCtrlPanel(final String viewId) {
		super(viewId, false);
	}
}
