package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends AppSecureCrudView<String, PositionType> {

	public PositionTypeCrudView() {
		super(CardType.POSITION_TYPE_CARD, "PT", "Position Type", new BasicApiKeyPanel("PL"));
	}

}
