package com.github.bordertech.corpdir.web.ui.dataapi;

/**
 * Data APi Types.
 *
 * The key is used for the DATA API instance and the matching Store and Action Creator.
 *
 * @author jonathan
 */
public enum DataApiType {
	POSITION("position"),
	ORG_UNIT("orgunit"),
	LOCATION("location"),
	POSITION_TYPE("positiontype"),
	UNIT_TYPE("unittype"),
	CONTACT("contact"),
	CONTACT_POSTION("contact-positions", false, true, false);

	DataApiType(final String key) {
		this(key, true, true, true);
	}

	DataApiType(final String key, final boolean entityStore, final boolean searchStore, final boolean actionCreator) {
		this.key = key;
		this.entityStore = entityStore;
		this.searchStore = searchStore;
		this.actionCreator = actionCreator;
	}

	final String key;
	boolean entityStore;
	boolean searchStore;
	boolean actionCreator;

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
