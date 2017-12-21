package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.PositionPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudTreeSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends AppSecureCrudWrapperView<String, Position> {

	public PositionCrudView() {
		super("POS", CardType.POSITION, new DefaultCrudTreeSmartView<String, Position>("SV", "Position", new PositionPanel("PL")));
	}

}
