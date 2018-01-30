package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.OrgUnitPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCardView extends AppSecureCrudCardView<OrgUnit> {

	public OrgUnitCardView() {
		super("OU", CardType.ORG_UNIT, new DefaultCorpCrudSmartView<OrgUnit>("SV", "Org Unit", new OrgUnitPanel("PL")));
	}

}
