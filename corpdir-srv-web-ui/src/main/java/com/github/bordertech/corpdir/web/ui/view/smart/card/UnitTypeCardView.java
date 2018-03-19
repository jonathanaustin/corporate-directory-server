package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.UnitTypePanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCardView extends DefaultCorpSecureCrudCardView<UnitType> {

	public UnitTypeCardView() {
		super("UT", CardType.UNIT_TYPE, new DefaultCorpCrudSmartView<UnitType>("SV", "Unit Type", new UnitTypePanel("PL")));
	}

}
