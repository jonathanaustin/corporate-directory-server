package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.OrgUnitPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudTreeSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCardTreeView extends AppSecureCrudCardView<OrgUnit> {

	public OrgUnitCardTreeView() {
		super("OU", CardType.ORG_UNIT, new DefaultCorpCrudTreeSmartView<OrgUnit>("SV", "Org Unit", new OrgUnitPanel("PL")));
	}

}
