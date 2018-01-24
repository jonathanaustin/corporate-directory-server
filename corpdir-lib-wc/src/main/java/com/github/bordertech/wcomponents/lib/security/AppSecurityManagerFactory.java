package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.didums.Didums;

/**
 * AppManager factory.
 *
 * @author Jonathan Austin
 */
public class AppSecurityManagerFactory {

	private static final AppSecurityManager INSTANCE = Didums.getService(AppSecurityManager.class);

	private AppSecurityManagerFactory() {
	}

	public static AppSecurityManager getInstance() {
		return INSTANCE;
	}

}
