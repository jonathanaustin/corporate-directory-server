package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.smart.panel.PositionPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudTreeSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends DefaultCrudTreeSmartView<String, Position> {

	public PositionCrudView() {
		super("POS", "Position", new PositionPanel("PL"));
	}

}
