package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;

/**
 * Unit type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends AppSecureCrudView<PositionType> {

	public PositionTypeCrudView() {
		super(CardType.POSITION_TYPE, "PT", "Position Type", new BasicApiKeyPanel("PL"));
	}

}
