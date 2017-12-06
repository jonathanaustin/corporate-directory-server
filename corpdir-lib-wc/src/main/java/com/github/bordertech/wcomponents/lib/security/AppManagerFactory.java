package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.locator.LocatorUtil;

/**
 * AppManager factory.
 *
 * @author Jonathan Austin
 */
public class AppManagerFactory {

	private static final AppManager INSTANCE = LocatorUtil.getService(AppManager.class);

	private AppManagerFactory() {
	}

	public static AppManager getInstance() {
		return INSTANCE;
	}

}
