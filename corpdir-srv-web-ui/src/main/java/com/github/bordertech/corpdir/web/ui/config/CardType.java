package com.github.bordertech.corpdir.web.ui.config;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.smart.card.ContactCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.LocationCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.OrgUnitCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.PositionCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.PositionTypeCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.SystemCtrlCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.UnitTypeCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.VersionCtrlCardView;
import com.github.bordertech.flux.wc.view.smart.secure.SecureCardView;

/**
 * Cards.
 *
 * @author jonathan
 */
public enum CardType {
	POSITION("position", "Position", false, PositionCardView.class, IconConstants.POSITION_IMAGE, DataApiType.POSITION),
	ORG_UNIT("orgunit", "Org Unit", false, OrgUnitCardView.class, IconConstants.ORG_UNIT_IMAGE, DataApiType.ORG_UNIT),
	LOCATION("location", "Location", true, LocationCardView.class, null, DataApiType.LOCATION),
	POSITION_TYPE("positiontype", "Position Type", true, PositionTypeCardView.class, null, DataApiType.POSITION_TYPE),
	CONTACT("contact", "Contact", false, ContactCardView.class, IconConstants.CONTACT_IMAGE, DataApiType.CONTACT),
	// System
	UNIT_TYPE("orgunittype", "Unit Type", true, UnitTypeCardView.class, null, DataApiType.UNIT_TYPE),
	VERSION_CTRL("version", "Version Ctrl", true, VersionCtrlCardView.class, null, DataApiType.VERSION_CTRL),
	SYSTEM_CTRL("system", "System Ctrl", true, SystemCtrlCardView.class, null, DataApiType.SYSTEM_CTRL);

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
