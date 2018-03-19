package com.github.bordertech.corpdir.web.ui.view.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.CardType;
import com.github.bordertech.corpdir.web.ui.CorpEntityType;
import com.github.bordertech.corpdir.web.ui.common.EntityLink;
import com.github.bordertech.corpdir.web.ui.common.EntityLinkRepeater;
import com.github.bordertech.corpdir.web.ui.view.dumb.BasicApiTreeablePanel;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.WLabel;

/**
 * Org Unit detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnitPanel extends BasicApiTreeablePanel<OrgUnit> {

	/**
	 * Construct basic org unit panel.
	 *
	 * @param viewId the viewId
	 */
	public OrgUnitPanel(final String viewId) {
		super("OrgUnit", viewId, CardType.ORG_UNIT);

		// Unit Type
		PollingDropdownOptionsView<String, UnitType> drpUnitType = new PollingDropdownOptionsView<>("UT");
		drpUnitType.setUseReadonlyContainer(true);
		drpUnitType.getReadonlyContainer().add(new EntityLink(CardType.UNIT_TYPE));
		WLabel lbl = new WLabel("Unit Type", drpUnitType.getSelectInput());
		getFormLayout().addField(lbl, drpUnitType);
		drpUnitType.setIncludeNullOption(true);
		drpUnitType.setCodeProperty("id");
		drpUnitType.getOptionsView().setBeanProperty("typeId");
		drpUnitType.setStoreKey(CorpEntityType.UNIT_TYPE.getStoreKey());

		// Manager Position
		PollingDropdownOptionsView<String, Position> drpMgrPos = new PollingDropdownOptionsView<>("MP");
		drpMgrPos.setUseReadonlyContainer(true);
		drpMgrPos.getReadonlyContainer().add(new EntityLink(CardType.POSITION));
		lbl = new WLabel("Manager Position", drpMgrPos.getSelectInput());
		getFormLayout().addField(lbl, drpMgrPos);
		drpMgrPos.setIncludeNullOption(true);
		drpMgrPos.setCodeProperty("id");
		drpMgrPos.getOptionsView().setBeanProperty("managerPosId");
		drpMgrPos.setStoreKey(CorpEntityType.POSITION.getStoreKey());

		// Positions in Org Unit
		PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");
		multiPos.setUseReadonlyContainer(true);
		multiPos.getReadonlyContainer().add(new EntityLinkRepeater(CardType.POSITION));
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setStoreKey(CorpEntityType.POSITION.getStoreKey());

		// FIXME: Temporary delays as firing extra AJAX Trigger
		drpUnitType.getPollingView().setPollingInterval(100);
		drpMgrPos.getPollingView().setPollingInterval(125);
		multiPos.getPollingView().setPollingInterval(150);
	}

}
