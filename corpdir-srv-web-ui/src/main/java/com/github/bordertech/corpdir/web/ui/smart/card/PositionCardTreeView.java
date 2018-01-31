package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.PositionPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudTreeSmartView;

/**
 * Position CRUD Tree view.
 *
 * @author jonathan
 */
public class PositionCardTreeView extends AppSecureCrudCardView<Position> {

	public PositionCardTreeView() {
		super("POS", CardType.POSITION, new DefaultCorpCrudTreeSmartView<Position>("SV", "Position", new PositionPanel("PL")));
	}

}
