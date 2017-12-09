package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.PositionPanel;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCrudView extends AppSecureCrudTreeView<Position> {

	public PositionCrudView() {
		super(CardType.POSITION, "POS", "Position", new PositionPanel("PL"));
	}

}
