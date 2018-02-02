package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.web.ui.actioncreator.ChannelActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.ContactActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.LocationActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.OrgUnitActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.PositionTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.SystemCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.UnitTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.actioncreator.VersionCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.CorpCrudStore;
import com.github.bordertech.corpdir.web.ui.store.ContactStore;
import com.github.bordertech.corpdir.web.ui.store.LocationStore;
import com.github.bordertech.corpdir.web.ui.store.OrgUnitStore;
import com.github.bordertech.corpdir.web.ui.store.PositionStore;
import com.github.bordertech.corpdir.web.ui.store.PositionTypeStore;
import com.github.bordertech.corpdir.web.ui.store.SystemCtrlStore;
import com.github.bordertech.corpdir.web.ui.store.UnitTypeStore;
import com.github.bordertech.corpdir.web.ui.store.VersionCtrlStore;
import java.util.HashSet;
import java.util.Set;

/**
 * CorpDir Entity Type and related Store and ActionCreator.
 *
 * @author jonathan
 */
public enum CorpEntityType {
	POSITION("position", true, PositionActionCreator.class, PositionStore.class),
	ORG_UNIT("orgunit", true, OrgUnitActionCreator.class, OrgUnitStore.class),
	LOCATION("location", false, LocationActionCreator.class, LocationStore.class),
	POSITION_TYPE("positiontype", false, PositionTypeActionCreator.class, PositionTypeStore.class),
	UNIT_TYPE("unittype", false, UnitTypeActionCreator.class, UnitTypeStore.class),
	CONTACT("contact", true, ContactActionCreator.class, ContactStore.class),
	VERSION_CTRL("versionctrl", true, VersionCtrlActionCreator.class, VersionCtrlStore.class),
	SYSTEM_CTRL("systemctrl", true, SystemCtrlActionCreator.class, SystemCtrlStore.class),
	CHANNEL("channel", false, ChannelActionCreator.class, null);

	CorpEntityType(final String key, final boolean linked, final Class<? extends CorpCrudActionCreator> actionCreatorClass, final Class<? extends CorpCrudStore> storeClass) {
		this.key = key;
		this.linked = linked;
		this.actionCreatorClass = actionCreatorClass;
		this.storeClass = storeClass;
	}

	final String key;
	final boolean linked;
	final Class<? extends CorpCrudActionCreator> actionCreatorClass;
	final Class<? extends CorpCrudStore> storeClass;

	public Class<? extends CorpCrudActionCreator> getActionCreatorClass() {
		return actionCreatorClass;
	}

	public Class<? extends CorpCrudStore> getStoreClass() {
		return storeClass;
	}

	public String getKey() {
		return key;
	}

	public String getStoreKey() {
		return isStore() ? "e-" + key : null;
	}

	public String getActionCreatorKey() {
		return isActionCreator() ? "a-" + key : null;
	}

	public boolean isLinked() {
		return linked;
	}

	public boolean isStore() {
		return storeClass != null;
	}

	public boolean isActionCreator() {
		return actionCreatorClass != null;
	}

	public static Set<String> getLinkedCreators(final CorpEntityType type) {
		Set<String> links = new HashSet<>();
		if (type.isActionCreator()) {
			links.add(type.getActionCreatorKey());
		}
		for (CorpEntityType ent : CorpEntityType.values()) {
			if (ent.isLinked()) {
				links.add(ent.getActionCreatorKey());
			}
		}
		return links;
	}

}
