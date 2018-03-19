package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.OrgUnitPanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudTreeSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCardTreeView extends DefaultCorpSecureCrudCardView<OrgUnit> {

	public OrgUnitCardTreeView() {
		super("OU", CardType.ORG_UNIT, new DefaultCorpCrudTreeSmartView<OrgUnit>("SV", "Org Unit", new OrgUnitPanel("PL")));
	}

}
