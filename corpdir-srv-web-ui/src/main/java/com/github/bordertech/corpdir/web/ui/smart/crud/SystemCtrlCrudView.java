package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.SystemCtrlPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class SystemCtrlCrudView extends AppSecureCrudWrapperView<String, SystemCtrl> {

	public SystemCtrlCrudView() {
		super("SC", CardType.SYSTEM_CTRL, new DefaultCrudSmartView<String, String, SystemCtrl>("SV", "System Ctrl", new SystemCtrlPanel("PL")));
	}

}
