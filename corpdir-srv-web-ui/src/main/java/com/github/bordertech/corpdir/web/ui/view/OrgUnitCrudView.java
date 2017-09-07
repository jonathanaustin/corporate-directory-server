package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.OrgUnitModel;
import com.github.bordertech.corpdir.web.ui.panel.OrgUnitPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class OrgUnitCrudView extends DefaultCrudView {

	private static final OrgUnitModel MODEL = new OrgUnitModel();

	public OrgUnitCrudView(final String qualifier) {
		super("Org Unit", MODEL, new OrgUnitPanel(), qualifier);
	}

}
