package com.github.bordertech.corpdir.web.ui.view;

import com.github.bordertech.corpdir.web.ui.panel.PositionPanel;
import com.github.bordertech.flux.wc.app.view.smart.crud.DefaultCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends DefaultCrudSmartView {

	public PositionCrudView() {
		super("Position", new PositionPanel());
		setSearchModelKey("position.search");
		setActionModelKey("position.action");
	}

}
