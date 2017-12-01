package com.github.bordertech.corpdir.web.ui.smart.panel;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.Position;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.corpdir.web.ui.dataapi.DataApiType;
import com.github.bordertech.flux.wc.view.smart.input.PollingDropdownOptionsView;
import com.github.bordertech.flux.wc.view.smart.input.PollingMultiSelectPairOptionsView;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import java.util.List;
import java.util.Objects;

/**
 * Org Unit detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnitPanel extends BasicApiKeyPanel<OrgUnit> {

	private final PollingDropdownOptionsView<String, OrgUnit> drpParent = new PollingDropdownOptionsView<>("PAR");
	private final PollingMultiSelectPairOptionsView<String, OrgUnit> multiSub = new PollingMultiSelectPairOptionsView<>("SUB");

	private final PollingDropdownOptionsView<String, UnitType> drpUnitType = new PollingDropdownOptionsView<>("UT");
	private final PollingDropdownOptionsView<String, Position> drpMgrPos = new PollingDropdownOptionsView<>("MP");
	private final PollingMultiSelectPairOptionsView<String, Position> multiPos = new PollingMultiSelectPairOptionsView<>("POS");

	/**
	 * Construct basic org unit panel.
	 *
	 * @param viewId the viewId
	 */
	public OrgUnitPanel(final String viewId) {
		super(viewId);

		// Parent
		WLabel lbl = new WLabel("Parent Org Unit", drpParent.getSelectInput());
		getFormLayout().addField(lbl, drpParent);
		drpParent.setIncludeNullOption(true);
		drpParent.setCodeProperty("id");
		drpParent.getOptionsView().setBeanProperty("parentId");
		drpParent.setStoreKey(DataApiType.ORG_UNIT.getSearchStoreKey());
		// FIXME
//		drpParent.setRetrieveListModelKey("orgunit.search");

		// Sub Org Units
		lbl = new WLabel("Sub Org Units", multiSub.getSelectInput());
		getFormLayout().addField(lbl, multiSub);
		multiSub.setCodeProperty("id");
		multiSub.getOptionsView().setBeanProperty("subIds");
		multiSub.setStoreKey(DataApiType.ORG_UNIT.getSearchStoreKey());
		// FIXME
//		multiSub.setRetrieveListModelKey("orgunit.search");

		// Unit Type
		lbl = new WLabel("Unit Type", drpUnitType.getSelectInput());
		getFormLayout().addField(lbl, drpUnitType);
		drpUnitType.setIncludeNullOption(true);
		drpUnitType.setCodeProperty("id");
		drpUnitType.getOptionsView().setBeanProperty("typeId");
		drpUnitType.setStoreKey(DataApiType.UNIT_TYPE.getSearchStoreKey());
		// FIXME
//		drpUnitType.setRetrieveListModelKey("unittype.search");

		// Manager Position
		lbl = new WLabel("Manager Position", drpMgrPos.getSelectInput());
		getFormLayout().addField(lbl, drpMgrPos);
		drpMgrPos.setIncludeNullOption(true);
		drpMgrPos.setCodeProperty("id");
		drpMgrPos.getOptionsView().setBeanProperty("managerPosId");
		drpMgrPos.setStoreKey(DataApiType.POSITION.getSearchStoreKey());
		// FIXME
//		drpMgrPos.setRetrieveListModelKey("position.search");

		// Positions in Org Unit
		lbl = new WLabel("Assigned positions", multiPos.getSelectInput());
		getFormLayout().addField(lbl, multiPos);
		multiPos.setCodeProperty("id");
		multiPos.getOptionsView().setBeanProperty("positionIds");
		multiPos.setStoreKey(DataApiType.POSITION.getSearchStoreKey());
		// FIXME
//		multiPos.setRetrieveListModelKey("position.search");

		// FIXME: Temporary delays as firing extra AJAX Trigger
		drpParent.getPollingView().setPollingInterval(50);
		multiSub.getPollingView().setPollingInterval(75);
		drpUnitType.getPollingView().setPollingInterval(100);
		drpMgrPos.getPollingView().setPollingInterval(125);
		multiPos.getPollingView().setPollingInterval(150);
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		// FIXME
		drpParent.doManualStart();
		multiSub.doManualStart();
		drpUnitType.doManualStart();
		drpMgrPos.doManualStart();
		multiPos.doManualStart();
	}

	@Override
	protected void validateComponent(final List<Diagnostic> diags) {
		super.validateComponent(diags);
		OrgUnit current = getViewBean();

		// Check parent
		OrgUnit parent = drpParent.getSelectedOption();
		if (Objects.equals(current, parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot make the Org Unit its own parent."));
		}

		// Check Sub
		List<OrgUnit> subs = multiSub.getSelectedOptions();
		if (subs != null && subs.contains(current)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot add the Org Unit to itself."));
		}
		if (subs != null && parent != null && subs.contains(parent)) {
			diags.add(createErrorDiagnostic(multiSub.getSelectInput(), "Cannot have the same Org Unit as a child and parent."));
		}
	}

}
