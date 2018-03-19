package com.github.bordertech.corpdir.web.ui.view.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.view.dumb.panel.PositionPanel;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpCrudTreeSmartView;
import com.github.bordertech.corpdir.web.ui.view.smart.DefaultCorpSecureCrudCardView;

/**
 * Position CRUD Tree view.
 *
 * @author jonathan
 */
public class PositionCardTreeView extends DefaultCorpSecureCrudCardView<Position> {

	public PositionCardTreeView() {
		super("POS", CardType.POSITION, new DefaultCorpCrudTreeSmartView<Position>("SV", "Position", new PositionPanel("PL")));
	}

}
