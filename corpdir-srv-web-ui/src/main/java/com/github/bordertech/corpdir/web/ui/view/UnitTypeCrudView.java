package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.lib.app.view.combo.DefaultCrudView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudView {

	public UnitTypeCrudView() {
		super("Unit Type", new BasicApiKeyPanel());
		setSearchModelKey("unittype.search");
		setActionModelKey("unittype.action");
	}

}
