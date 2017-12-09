package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends AppSecureCrudView<UnitType> {

	public UnitTypeCrudView() {
		super(CardType.UNIT_TYPE, "UT", "Unit Type", new BasicApiKeyPanel("PL"));
	}

}
