package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCardView extends AppSecureCrudCardView<UnitType> {

	public UnitTypeCardView() {
		super("UT", CardType.UNIT_TYPE, new DefaultCorpCrudSmartView<UnitType>("SV", "Unit Type", new BasicApiKeyPanel("PL")));
	}

}
