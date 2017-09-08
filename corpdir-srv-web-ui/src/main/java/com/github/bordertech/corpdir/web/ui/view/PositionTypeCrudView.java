package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.PositionTypeModel;
import com.github.bordertech.corpdir.web.ui.panel.BasicApiKeyPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends DefaultCrudView {

	private static final PositionTypeModel MODEL = new PositionTypeModel();

	public PositionTypeCrudView() {
		super("Position Type", MODEL, new BasicApiKeyPanel());
	}

}
