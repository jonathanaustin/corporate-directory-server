package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.PositionTypeModel;
import com.github.bordertech.corpdir.web.ui.common.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView2;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends DefaultCrudView2 {

	private static final PositionTypeModel MODEL = new PositionTypeModel();

	public PositionTypeCrudView(final String qualifier) {
		super("Position Type", MODEL, new BasicApiKeyPanel(), qualifier);
	}

}
