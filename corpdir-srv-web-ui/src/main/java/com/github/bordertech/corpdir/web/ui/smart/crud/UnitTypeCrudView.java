package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.smart.panel.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudSmartView<String, UnitType> {

	public UnitTypeCrudView() {
		super("UT", "Unit Type", new BasicApiKeyPanel("PL"));
	}

}
