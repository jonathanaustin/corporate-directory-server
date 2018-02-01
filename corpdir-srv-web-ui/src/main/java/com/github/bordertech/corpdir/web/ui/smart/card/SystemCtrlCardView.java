package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.SystemCtrl;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.SystemCtrlPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class SystemCtrlCardView extends AppSecureCrudCardView<SystemCtrl> {

	public SystemCtrlCardView() {
		super("SC", CardType.SYSTEM_CTRL, new DefaultCorpCrudSmartView<SystemCtrl>("SV", "System Ctrl", new SystemCtrlPanel("PL")));
	}

}
