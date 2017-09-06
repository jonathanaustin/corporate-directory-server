package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.UnitTypeModel;
import com.github.bordertech.corpdir.web.ui.panel.BasicEntityPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView2;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudView2 {

	private static final UnitTypeModel MODEL = new UnitTypeModel();

	public UnitTypeCrudView(final String qualifier) {
		super("Unit Type", MODEL, new BasicEntityPanel(), qualifier);
	}

}
