package com.github.bordertech.corpdir.web.ui.smart.crud;

import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiKeyPanel;
import com.github.bordertech.flux.wc.view.smart.crud.DefaultCrudSmartView;

/**
 * Position type crud view.
 *
 * @author jonathan
 */
public class PositionTypeCrudView extends AppSecureCrudWrapperView<String, PositionType> {

	public PositionTypeCrudView() {
		super("PT", CardType.POSITION_TYPE, new DefaultCrudSmartView<String, PositionType>("SV", "Position Type", new BasicApiKeyPanel("PL")));
	}

}
