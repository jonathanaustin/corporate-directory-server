package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.locator.LocatorUtil;

/**
 * AppManager factory.
 *
 * @author Jonathan Austin
 */
public class AppSecurityManagerFactory {

	private static final AppSecurityManager INSTANCE = LocatorUtil.getService(AppSecurityManager.class);

	private AppSecurityManagerFactory() {
	}

	public static AppSecurityManager getInstance() {
		return INSTANCE;
	}

}
