package com.github.bordertech.corpdir.web.ui.event;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.view.ContactCrudView;
import com.github.bordertech.corpdir.web.ui.view.LocationCrudView;
import com.github.bordertech.corpdir.web.ui.view.OrgUnitCrudView;
import com.github.bordertech.corpdir.web.ui.view.PositionCrudView;
import com.github.bordertech.corpdir.web.ui.view.PositionTypeCrudView;
import com.github.bordertech.corpdir.web.ui.view.UnitTypeCrudView;
import com.github.bordertech.flux.wc.view.View;

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

	CardType(final String desc, final boolean system, final Class<? extends View> clazz, final String imageUrl) {
		this.desc = desc;
		this.system = system;
		this.clazz = clazz;
		this.imageUrl = imageUrl;
	}

	final String desc;
	final boolean system;
	final Class<? extends View> clazz;
	final String imageUrl;

	public String getDesc() {
		return desc;
	}

	public boolean isSystem() {
		return system;
	}

	public Class<? extends View> getClazz() {
		return clazz;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public View createCardViewInstance() {
		try {
			return (View) getClazz().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
