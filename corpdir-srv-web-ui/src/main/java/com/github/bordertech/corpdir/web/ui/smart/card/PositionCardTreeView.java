package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.PositionPanel;
import com.github.bordertech.corpdir.web.ui.flux.view.impl.DefaultCorpCrudTreeSmartView;
import com.github.bordertech.corpdir.web.ui.flux.view.impl.DefaultCorpSecureCrudCardView;

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
