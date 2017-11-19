package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.smart.panel.OrgUnitPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCrudView extends DefaultCrudSmartView {

	public OrgUnitCrudView() {
		super("OU", "Org Unit", new OrgUnitPanel("PL"));
	}

}
