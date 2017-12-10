package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.SystemCtrlPanel;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class SystemCtrlCrudView extends AppSecureCrudView<SystemCtrl> {

	public SystemCtrlCrudView() {
		super(CardType.SYSTEM_CTRL, "SC", "System Ctrl", new SystemCtrlPanel("PL"));
	}

}
