package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.OrgUnitPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCrudView extends DefaultCrudSmartView {

	public OrgUnitCrudView() {
		super("Org Unit", new OrgUnitPanel());
		setSearchModelKey("orgunit.search");
		setActionModelKey("orgunit.action");
	}

}
