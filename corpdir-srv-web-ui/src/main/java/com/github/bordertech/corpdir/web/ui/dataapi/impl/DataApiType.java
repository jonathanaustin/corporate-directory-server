package com.github.bordertech.corpdir.web.ui.dataapi.impl;

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
	CONTACT_POSTION("contact-positions", true, false);

	DataApiType(final String key) {
		this(key, true, true);
	}

	DataApiType(final String key, final boolean store, final boolean actionCreator) {
		this.key = key;
		this.store = store;
		this.actionCreator = actionCreator;
	}

	final String key;
	boolean store;
	boolean actionCreator;

	public String getKey() {
		return key;
	}

	public boolean isStore() {
		return store;
	}

	public boolean isActionCreator() {
		return actionCreator;
	}

}
