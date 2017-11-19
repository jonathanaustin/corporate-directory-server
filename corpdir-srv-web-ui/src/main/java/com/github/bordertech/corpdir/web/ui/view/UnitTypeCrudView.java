package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudSmartView {

	public UnitTypeCrudView() {
		super("Unit Type", new BasicApiKeyPanel());
		setSearchModelKey("unittype.search");
		setActionModelKey("unittype.action");
	}

}
