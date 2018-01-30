package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.OrgUnitPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudTreeSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCrudView extends AppSecureCrudWrapperView<String, OrgUnit> {

	public OrgUnitCrudView() {
		super("OU", CardType.ORG_UNIT, new DefaultCrudTreeSmartView<String, String, OrgUnit>("SV", "Org Unit", new OrgUnitPanel("PL")));
	}

}
