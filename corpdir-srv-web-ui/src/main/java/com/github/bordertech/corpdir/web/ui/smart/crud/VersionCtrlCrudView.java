package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.VersionCtrl;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiIdPanel;

/**
 * Version Ctrl crud view.
 *
 * @author jonathan
 */
public class VersionCtrlCrudView extends AppSecureCrudView<VersionCtrl> {

	public VersionCtrlCrudView() {
		super(CardType.VERSION_CTRL, "VC", "Version Ctrl", new BasicApiIdPanel("PL"));
	}

}
