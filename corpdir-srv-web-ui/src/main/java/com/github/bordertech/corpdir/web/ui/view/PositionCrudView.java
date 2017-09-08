package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.model.PositionModel;
import com.github.bordertech.corpdir.web.ui.panel.PositionPanel;
import com.github.bordertech.wcomponents.lib.app.combo.DefaultCrudView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends DefaultCrudView {

	private static final PositionModel MODEL = new PositionModel();

	public PositionCrudView() {
		super("Position", MODEL, new PositionPanel());
	}

}
