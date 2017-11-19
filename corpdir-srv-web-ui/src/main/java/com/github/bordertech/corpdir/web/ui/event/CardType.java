package com.github.bordertech.corpdir.web.ui.event;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.smart.crud.ContactCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.LocationCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.OrgUnitCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.PositionCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.PositionTypeCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.UnitTypeCrudView;
import com.github.bordertech.flux.view.SmartView;

/**
 * Cards.
 *
 * @author jonathan
 */
public enum CardType {
	POSITION_CARD("Position", false, PositionCrudView.class, IconConstants.POSITION_IMAGE),
	ORG_UNIT_CARD("Org Unit", false, OrgUnitCrudView.class, IconConstants.ORG_UNIT_IMAGE),
	LOCATION_CARD("Location", true, LocationCrudView.class, null),
	POSITION_TYPE_CARD("Position Type", true, PositionTypeCrudView.class, null),
	UNIT_TYPE_CARD("Unit Type", true, UnitTypeCrudView.class, null),
	CONTACT_CARD("Contact", false, ContactCrudView.class, IconConstants.CONTACT_IMAGE);

	CardType(final String desc, final boolean system, final Class<? extends SmartView> clazz, final String imageUrl) {
		this.desc = desc;
		this.system = system;
		this.clazz = clazz;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final boolean system;
	final Class<? extends SmartView> clazz;
	final String imageUrl;

	public String getDesc() {
		return desc;
	}

	public boolean isSystem() {
		return system;
	}

	public Class<? extends SmartView> getClazz() {
		return clazz;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public SmartView createCardViewInstance() {
		try {
			return (SmartView) getClazz().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
