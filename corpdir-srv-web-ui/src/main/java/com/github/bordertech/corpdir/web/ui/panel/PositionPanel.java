package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.Contact;
import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.PositionType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingDropdownOptionsView;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.List;
import jersey.repackaged.com.google.common.base.Objects;

/**
 * Position detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PositionPanel extends BasicApiKeyPanel<Position> {

	private final PollingDropdownOptionsView<String, Position> drpParent = new PollingDropdownOptionsView<>("PAR");
	private final PollingMultiSelectPairOptionsView<String, Position> multiSub = new PollingMultiSelectPairOptionsView<>("SUB");

	private final PollingDropdownOptionsView<String, PositionType> drpUnitType = new PollingDropdownOptionsView<>("UT");
	private final PollingDropdownOptionsView<String, OrgUnit> drpOwnerOU = new PollingDropdownOptionsView<>("BOU");
	private final PollingMultiSelectPairOptionsView<String, OrgUnit> multiOUs = new PollingMultiSelectPairOptionsView<>("MOU");
	private final PollingMultiSelectPairOptionsView<String, Contact> multiContact = new PollingMultiSelectPairOptionsView<>("CON");

	/**
	 * Construct basic position panel.
	 */
	public PositionPanel() {
		super();
		// Parent
		WLabel lbl = new WLabel("Reports to postion", drpParent.getSelectInput());
		getFormLayout().addField(lbl, drpParent);
		drpParent.setIncludeNullOption(true);
		drpParent.setCodeProperty("id");
		drpParent.getOptionsView().setBeanProperty("parentId");
		drpParent.setRetrieveListModelKey("position.search");

		// Sub Positions
		lbl = new WLabel("Looks after positions", multiSub.getSelectInput());
		getFormLayout().addField(lbl, multiSub);
		multiSub.setCodeProperty("id");
		multiSub.getOptionsView().setBeanProperty("subIds");
		multiSub.setRetrieveListModelKey("position.search");

		// Unit Type
		lbl = new WLabel("Position Type", drpUnitType.getSelectInput());
		getFormLayout().addField(lbl, drpUnitType);
		drpUnitType.setIncludeNullOption(true);
		drpUnitType.setCodeProperty("id");
		drpUnitType.getOptionsView().setBeanProperty("typeId");
		drpUnitType.setRetrieveListModelKey("unittype.search");

		// Owner OU
		lbl = new WLabel("Owner Org Unit", drpOwnerOU.getSelectInput());
		getFormLayout().addField(lbl, drpOwnerOU);
		drpOwnerOU.setIncludeNullOption(true);
		drpOwnerOU.setCodeProperty("id");
		drpOwnerOU.getOptionsView().setBeanProperty("ouId");
		drpOwnerOU.setRetrieveListModelKey("orgunit.search");

		// Assigned OU
		lbl = new WLabel("Assigned Org Units", multiOUs.getSelectInput());
		getFormLayout().addField(lbl, multiOUs);
		multiOUs.setCodeProperty("id");
		multiOUs.getOptionsView().setBeanProperty("manageOuIds");
		multiOUs.setRetrieveListModelKey("orgunit.search");

		// Assigned Contacts
		lbl = new WLabel("Assigned contacts", multiContact.getSelectInput());
		getFormLayout().addField(lbl, multiContact);
		multiContact.setCodeProperty("id");
		multiContact.getOptionsView().setBeanProperty("contactIds");
		multiContact.setRetrieveListModelKey("contact.search");

		// FIXME: Temporary delays as firing extra AJX Trigger
		drpParent.getPollingView().setPollingInterval(50);
		multiSub.getPollingView().setPollingInterval(75);
		drpUnitType.getPollingView().setPollingInterval(100);
		drpOwnerOU.getPollingView().setPollingInterval(125);
		multiOUs.getPollingView().setPollingInterval(150);
		multiContact.getPollingView().setPollingInterval(175);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		drpParent.startLoad("");
		multiSub.startLoad("");
		drpUnitType.startLoad("");
		drpOwnerOU.startLoad("");
		multiOUs.startLoad("");
		multiContact.startLoad("");
	}

	@Override
	protected void validateComponent(final List<Diagnostic> diags) {
		super.validateComponent(diags);
		Position current = getViewBean();

		// Check parent
		Position parent = drpParent.getSelectedOption();
		if (Objects.equal(current, parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot make the Position its own parent."));
		}

		// Check Sub
		List<Position> subs = multiSub.getSelectedOptions();
		if (subs != null && subs.contains(current)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot add the Position to itself."));
		}
		if (subs != null && parent != null && subs.contains(parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot have the same Position as a child and parent."));
		}
	}

}
