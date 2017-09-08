package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.UnitTypeModel;
import com.github.bordertech.corpdir.web.ui.panel.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class UnitTypeCrudView extends DefaultCrudView {

	private static final UnitTypeModel MODEL = new UnitTypeModel();

	public UnitTypeCrudView() {
		super("Unit Type", MODEL, new BasicApiKeyPanel());
	}

}
