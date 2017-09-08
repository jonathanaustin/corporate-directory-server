package com.github.bordertech.corpdir.web.ui.event;

import com.github.bordertech.corpdir.web.ui.view.ContactCrudView;
import com.github.bordertech.corpdir.web.ui.view.LocationCrudView;
import com.github.bordertech.corpdir.web.ui.view.OrgUnitCrudView;
import com.github.bordertech.corpdir.web.ui.view.PositionCrudView;
import com.github.bordertech.corpdir.web.ui.view.PositionTypeCrudView;
import com.github.bordertech.corpdir.web.ui.view.UnitTypeCrudView;
import com.github.bordertech.wcomponents.lib.mvc.View;

/**
 * Cards.
 *
 * @author jonathan
 */
public enum CardType {
	POSITION_CARD("Position", false, PositionCrudView.class),
	ORG_UNIT_CARD("Org Unit", false, OrgUnitCrudView.class),
	LOCATION_CARD("Location", true, LocationCrudView.class),
	POSITION_TYPE_CARD("Position Type", true, PositionTypeCrudView.class),
	UNIT_TYPE_CARD("Unit Type", true, UnitTypeCrudView.class),
	CONTACT_CARD("Contact", false, ContactCrudView.class);

	CardType(final String desc, final boolean system, final Class<? extends View> clazz) {
		this.desc = desc;
		this.system = system;
		this.clazz = clazz;
	}

	final String desc;
	final boolean system;
	final Class<? extends View> clazz;

	public String getDesc() {
		return desc;
	}

	public boolean isSystem() {
		return system;
	}

	public Class<? extends View> getClazz() {
		return clazz;
	}

	public View createCardViewInstance(final String qualifier) {
		try {
			return (View) getClazz().getConstructor(String.class).newInstance(qualifier);
		} catch (Exception e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
