package com.github.bordertech.corpdir.web.ui.config;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.smart.crud.ContactCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.LocationCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.OrgUnitCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.PositionCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.PositionTypeCrudView;
import com.github.bordertech.corpdir.web.ui.smart.crud.UnitTypeCrudView;
import com.github.bordertech.flux.wc.view.smart.secure.SecureCardView;

/**
 * Cards.
 *
 * @author jonathan
 */
public enum CardType {
	POSITION_CARD("position", "Position", false, PositionCrudView.class, IconConstants.POSITION_IMAGE, DataApiType.POSITION),
	ORG_UNIT_CARD("orgunit", "Org Unit", false, OrgUnitCrudView.class, IconConstants.ORG_UNIT_IMAGE, DataApiType.ORG_UNIT),
	LOCATION_CARD("location", "Location", true, LocationCrudView.class, null, DataApiType.LOCATION),
	POSITION_TYPE_CARD("positiontype", "Position Type", true, PositionTypeCrudView.class, null, DataApiType.POSITION_TYPE),
	UNIT_TYPE_CARD("orgunittype", "Unit Type", true, UnitTypeCrudView.class, null, DataApiType.UNIT_TYPE),
	CONTACT_CARD("contact", "Contact", false, ContactCrudView.class, IconConstants.CONTACT_IMAGE, DataApiType.CONTACT);

	CardType(final String path, final String desc, final boolean system, final Class<? extends SecureCardView> clazz, final String imageUrl, final DataApiType apiType) {
		this.path = path;
		this.desc = desc;
		this.system = system;
		this.clazz = clazz;
		this.imageUrl = imageUrl;
		this.apiType = apiType;
	}

	final String path;
	final String desc;
	final boolean system;
	final Class<? extends SecureCardView> clazz;
	final String imageUrl;
	final DataApiType apiType;

	public String getPath() {
		return path;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isSystem() {
		return system;
	}

	public Class<? extends SecureCardView> getClazz() {
		return clazz;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public DataApiType getApiType() {
		return apiType;
	}

	public SecureCardView createCardViewInstance() {
		try {
			return (SecureCardView) getClazz().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
