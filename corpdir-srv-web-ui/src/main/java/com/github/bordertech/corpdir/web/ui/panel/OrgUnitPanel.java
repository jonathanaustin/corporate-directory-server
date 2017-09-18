package com.github.bordertech.corpdir.web.ui.panel;

import com.github.bordertech.corpdir.api.v1.model.OrgUnit;
import com.github.bordertech.corpdir.api.v1.model.UnitType;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WLabel;
import com.github.bordertech.wcomponents.lib.app.event.PollingEventType;
import com.github.bordertech.wcomponents.lib.app.view.combo.input.PollingDropdownOptionsView;

/**
 * Org Unit detail Form.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class OrgUnitPanel extends BasicApiKeyPanel<OrgUnit> {

	private final PollingDropdownOptionsView<String, UnitType> drpUnitType = new PollingDropdownOptionsView<>();

// TODO
//	private String typeId;
//	private String managerPosId;
//	private List<String> positionIds;
	/**
	 * Construct basic detail panel. \
	 */
	public OrgUnitPanel() {
		super();

		// Unit Type
		drpUnitType.setQualifier("X");
		drpUnitType.setQualifierContext(true);
		WLabel lblUnitType = new WLabel("Unit Type", drpUnitType.getSelectInput());
		getFormLayout().addField(lblUnitType, drpUnitType);
		drpUnitType.setRetrieveCollectionModelKey("unittype.search");
	}

	@Override
	protected void initViewContent(final Request request) {
		super.initViewContent(request);
		// Unit Type
		drpUnitType.configViews();
		drpUnitType.dispatchEvent(PollingEventType.START_POLLING, "");
	}

//	@Override
//	public void updateBeanValue() {
//		super.updateBeanValue();
//		Contact bean = getViewBean();
//		// Positions
//		List<Position> positions = (List<Position>) selectView.getCollectionView().getBeanValue();
//		bean.setPositionIds(ApiModelUtil.convertApiObjectsToIds(positions));
//	}
}
