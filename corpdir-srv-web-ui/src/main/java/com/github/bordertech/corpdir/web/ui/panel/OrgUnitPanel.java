package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingDropdownOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingMultiSelectPairOptionsView;

/**
 * Org Unit detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnitPanel extends BasicApiKeyPanel<OrgUnit> {

	private final PollingDropdownOptionsView<String, UnitType> drpUnitType = new PollingDropdownOptionsView<>("UT");
	private final PollingDropdownOptionsView<String, Position> drpMgrPos = new PollingDropdownOptionsView<>("MP");
	private final PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");

	/**
	 * Construct basic detail panel. \
	 */
	public OrgUnitPanel() {
		super();

		// Unit Type
		WLabel lblUnitType = new WLabel("Unit Type", drpUnitType.getSelectInput());
		getFormLayout().addField(lblUnitType, drpUnitType);
		drpUnitType.setIncludeNullOption(true);
		drpUnitType.setCodeProperty("id");
		drpUnitType.getOptionsView().setBeanProperty("typeId");
		drpUnitType.setRetrieveCollectionModelKey("unittype.search");

		// Manager Position
		WLabel lblMgrPos = new WLabel("Manager Position", drpMgrPos.getSelectInput());
		getFormLayout().addField(lblMgrPos, drpMgrPos);
		drpMgrPos.setIncludeNullOption(true);
		drpMgrPos.setCodeProperty("id");
		drpMgrPos.getOptionsView().setBeanProperty("managerPosId");
		drpMgrPos.setRetrieveCollectionModelKey("position.search");

		// Positions in Org Unit
		WLabel lblPos = new WLabel("Positions", multiPos.getSelectInput());
		getFormLayout().addField(lblPos, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setRetrieveCollectionModelKey("position.search");

		// FIXME: Temporary delays as firing extra AJX Trigger
		drpUnitType.getPollingView().setPollingInterval(100);
		drpMgrPos.getPollingView().setPollingInterval(200);
		multiPos.getPollingView().setPollingInterval(300);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		drpUnitType.startLoad("");
		drpMgrPos.startLoad("");
		multiPos.startLoad("");
	}
}
