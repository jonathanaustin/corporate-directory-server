package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiIdPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class VersionCtrlCrudView extends AppSecureCrudWrapperView<String, VersionCtrl> {

	public VersionCtrlCrudView() {
		super("VC", CardType.VERSION_CTRL, new DefaultCrudSmartView<String, VersionCtrl>("SV", "Version Ctrl", new BasicApiIdPanel("PL")));
	}

}
