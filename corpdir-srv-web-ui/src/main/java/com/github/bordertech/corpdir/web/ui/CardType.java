package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.web.ui.common.IconConstants;
import com.github.bordertech.corpdir.web.ui.smart.card.ContactCardView;
import com.github.bordertech.corpdir.web.ui.smart.card.LocationCardTreeView;
import com.github.bordertech.corpdir.web.ui.smart.card.OrgUnitCardTreeView;
import com.github.bordertech.corpdir.web.ui.smart.card.PositionCardTreeView;
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
	POSITION("position", "Position", false, PositionCardTreeView.class, IconConstants.POSITION_IMAGE, CorpEntityType.POSITION),
	ORG_UNIT("orgunit", "Org Unit", false, OrgUnitCardTreeView.class, IconConstants.ORG_UNIT_IMAGE, CorpEntityType.ORG_UNIT),
	LOCATION("location", "Location", true, LocationCardTreeView.class, null, CorpEntityType.LOCATION),
	POSITION_TYPE("positiontype", "Position Type", true, PositionTypeCardView.class, null, CorpEntityType.POSITION_TYPE),
	CONTACT("contact", "Contact", false, ContactCardView.class, IconConstants.CONTACT_IMAGE, CorpEntityType.CONTACT),
	// System
	UNIT_TYPE("orgunittype", "Unit Type", true, UnitTypeCardView.class, null, CorpEntityType.UNIT_TYPE),
	VERSION_CTRL("version", "Version Ctrl", true, VersionCtrlCardView.class, null, CorpEntityType.VERSION_CTRL),
	SYSTEM_CTRL("system", "System Ctrl", true, SystemCtrlCardView.class, null, CorpEntityType.SYSTEM_CTRL);

	CardType(final String path, final String desc, final boolean system, final Class<? extends SecureCardView> clazz, final String imageUrl, final CorpEntityType apiType) {
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
	final CorpEntityType apiType;

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

	public CorpEntityType getApiType() {
		return apiType;
	}

	public SecureCardView createCardViewInstance() {
		try {
			return (SecureCardView) getClazz().newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new IllegalStateException("Could not create view class. " + e.getMessage(), e);
		}
	}
}
