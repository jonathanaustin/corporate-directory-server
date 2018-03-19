package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.VersionCtrlPanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class VersionCtrlCardView extends DefaultCorpSecureCrudCardView<VersionCtrl> {

	public VersionCtrlCardView() {
		super("VC", CardType.VERSION_CTRL, new DefaultCorpCrudSmartView<VersionCtrl>("SV", "Version Ctrl", new VersionCtrlPanel("PL")));
	}

}
