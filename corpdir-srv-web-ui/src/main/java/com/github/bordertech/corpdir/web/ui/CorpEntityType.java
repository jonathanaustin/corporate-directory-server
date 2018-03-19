package com.github.bordertech.corpdir.web.ui;

import com.github.bordertech.corpdir.web.ui.flux.actioncreator.CorpCrudActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.ChannelActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.ContactActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.LocationActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.OrgUnitActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.PositionActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.PositionTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.SystemCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.UnitTypeActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.actioncreator.impl.VersionCtrlActionCreator;
import com.github.bordertech.corpdir.web.ui.flux.store.CorpCrudStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.ContactStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.LocationStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.OrgUnitStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.PositionStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.PositionTypeStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.SystemCtrlStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.UnitTypeStore;
import com.github.bordertech.corpdir.web.ui.flux.store.impl.VersionCtrlStore;
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
