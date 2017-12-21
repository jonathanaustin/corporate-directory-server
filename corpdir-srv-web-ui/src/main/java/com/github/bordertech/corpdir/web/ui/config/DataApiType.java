package com.github.bordertech.corpdir.web.ui.config;

/**
 * Data APi Types.
 *
 * The key is used for the DATA API instance and the matching Store and Action Creator.
 *
 * @author jonathan
 */
public enum DataApiType {
	POSITION("position", true),
	ORG_UNIT("orgunit", true),
	LOCATION("location"),
	POSITION_TYPE("positiontype"),
	UNIT_TYPE("unittype"),
	CONTACT("contact", true),
	VERSION_CTRL("versionctrl", true),
	SYSTEM_CTRL("systemctrl", true),
	CHANNEL("channel", false, false, false, true);

	DataApiType(final String key) {
		this(key, false);
	}

	DataApiType(final String key, final boolean linked) {
		this(key, linked, true, true, true);
	}

	DataApiType(final String key, final boolean linked, final boolean entityStore, final boolean searchStore, final boolean actionCreator) {
		this.key = key;
		this.linked = linked;
		this.entityStore = entityStore;
		this.searchStore = searchStore;
		this.actionCreator = actionCreator;
	}

	final String key;
	boolean entityStore;
	boolean searchStore;
	boolean actionCreator;
	boolean linked;

	public String getKey() {
		return key;
	}

	public String getEntityStoreKey() {
		return "e-" + key;
	}

	public String getSearchStoreKey() {
		return "s-" + key;
	}

	public String getActionCreatorKey() {
		return "a-" + key;
	}

	public boolean isLinked() {
		return linked;
	}

	public boolean isEntityStore() {
		return entityStore;
	}

	public boolean isSearchStore() {
		return searchStore;
	}

	public boolean isActionCreator() {
		return actionCreator;
	}

}
