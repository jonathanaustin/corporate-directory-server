package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends DefaultCrudSmartView {

	public PositionTypeCrudView() {
		super("Position Type", new BasicApiKeyPanel());
		setSearchModelKey("positiontype.search");
		setActionModelKey("positiontype.action");
	}

}
