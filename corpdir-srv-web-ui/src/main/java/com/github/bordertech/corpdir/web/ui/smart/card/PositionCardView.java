package com.github.bordertech.corpdir.web.ui.smart.card;

import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.panel.PositionPanel;
import com.github.bordertech.corpdir.web.ui.smart.crud.DefaultCorpCrudSmartView;

/**
 * Org Unit crud view.
 *
 * @author jonathan
 */
public class PositionCardView extends AppSecureCrudCardView<Position> {

	public PositionCardView() {
		super("POS", CardType.POSITION, new DefaultCorpCrudSmartView<Position>("SV", "Position", new PositionPanel("PL")));
	}

}
