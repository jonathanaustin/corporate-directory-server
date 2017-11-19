package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.web.ui.smart.panel.PositionPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends DefaultCrudSmartView {

	public PositionCrudView() {
		super("POS", "Position", new PositionPanel("PL"));
//		setSearchModelKey("position.search");
//		setActionModelKey("position.action");
	}

}
