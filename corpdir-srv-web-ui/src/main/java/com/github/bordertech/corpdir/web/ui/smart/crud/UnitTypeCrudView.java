package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends AppSecureCrudWrapperView<String, UnitType> {

	public UnitTypeCrudView() {
		super("UT", CardType.UNIT_TYPE, new DefaultCrudSmartView<String, UnitType>("SV", "Unit Type", new BasicApiKeyPanel("PL")));
	}

}
