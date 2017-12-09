package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.OrgUnitPanel;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCrudView extends AppSecureCrudTreeView<OrgUnit> {

	public OrgUnitCrudView() {
		super(CardType.ORG_UNIT, "OU", "Org Unit", new OrgUnitPanel("PL"));
	}

}
