package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.smart.panel.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudSmartView {

	public UnitTypeCrudView() {
		super("UT", "Unit Type", new BasicApiKeyPanel("PL"));
	}

}
