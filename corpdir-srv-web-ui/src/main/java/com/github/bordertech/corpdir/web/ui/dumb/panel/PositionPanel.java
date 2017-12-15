package com.github.bordertech.corpdir.web.ui.dumb.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.corpdir.web.ui.common.EntityLink;
import com.github.bordertech.corpdir.web.ui.common.EntityLinkRepeater;
import com.github.bordertech.corpdir.web.ui.config.CardType;
import com.github.bordertech.corpdir.web.ui.config.DataApiType;
import com.github.bordertech.corpdir.web.ui.dumb.BasicApiTreeablePanel;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.WLabel;

/**
 * Position detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PositionPanel extends BasicApiTreeablePanel<Position> {

	/**
	 * Construct basic position panel.
	 *
	 * @param viewId the viewId
	 */
	public PositionPanel(final String viewId) {
		super("Position", viewId, CardType.POSITION);

		// Position Type
		PollingDropdownOptionsView<String, PositionType> drpPosType = new PollingDropdownOptionsView<>("PT");
		drpPosType.setUseReadonlyContainer(true);
		drpPosType.getReadonlyContainer().add(new EntityLink(CardType.POSITION_TYPE));
		WLabel lbl = new WLabel("Position Type", drpPosType.getSelectInput());
		getFormLayout().addField(lbl, drpPosType);
		drpPosType.setIncludeNullOption(true);
		drpPosType.setCodeProperty("id");
		drpPosType.getOptionsView().setBeanProperty("typeId");
		drpPosType.setStoreKey(DataApiType.POSITION_TYPE.getSearchStoreKey());

		// Owner OU
		PollingDropdownOptionsView<String, OrgUnit> drpOwnerOU = new PollingDropdownOptionsView<>("BOU");
		drpOwnerOU.setUseReadonlyContainer(true);
		drpOwnerOU.getReadonlyContainer().add(new EntityLink(CardType.ORG_UNIT));
		lbl = new WLabel("Owner Org Unit", drpOwnerOU.getSelectInput());
		getFormLayout().addField(lbl, drpOwnerOU);
		drpOwnerOU.setIncludeNullOption(true);
		drpOwnerOU.setCodeProperty("id");
		drpOwnerOU.getOptionsView().setBeanProperty("ouId");
		drpOwnerOU.setStoreKey(DataApiType.ORG_UNIT.getSearchStoreKey());

		// Assigned OU
		PollingMultiSelectPairOptionsView<String, OrgUnit> multiOUs = new PollingMultiSelectPairOptionsView<>("MOU");
		multiOUs.setUseReadonlyContainer(true);
		multiOUs.getReadonlyContainer().add(new EntityLinkRepeater(CardType.ORG_UNIT));
		lbl = new WLabel("Assigned Org Units", multiOUs.getSelectInput());
		getFormLayout().addField(lbl, multiOUs);
		multiOUs.setCodeProperty("id");
		multiOUs.getOptionsView().setBeanProperty("manageOuIds");
		multiOUs.setStoreKey(DataApiType.ORG_UNIT.getSearchStoreKey());

		// Assigned Contacts
		PollingMultiSelectPairOptionsView<String, Contact> multiContact = new PollingMultiSelectPairOptionsView<>("CON");
		multiContact.setUseReadonlyContainer(true);
		multiContact.getReadonlyContainer().add(new EntityLinkRepeater(CardType.CONTACT));
		lbl = new WLabel("Assigned contacts", multiContact.getSelectInput());
		getFormLayout().addField(lbl, multiContact);
		multiContact.setCodeProperty("id");
		multiContact.getOptionsView().setBeanProperty("contactIds");
		multiContact.setStoreKey(DataApiType.CONTACT.getSearchStoreKey());

		// FIXME: Temporary delays as firing extra AJAX Trigger
		drpPosType.getPollingView().setPollingInterval(100);
		drpOwnerOU.getPollingView().setPollingInterval(125);
		multiOUs.getPollingView().setPollingInterval(150);
		multiContact.getPollingView().setPollingInterval(175);
	}

}
