package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.UnitTypePanel;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpCrudSmartView;
import com.github.bordertech.corpdir.web.ui.flux.impl.DefaultCorpSecureCrudCardView;

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
