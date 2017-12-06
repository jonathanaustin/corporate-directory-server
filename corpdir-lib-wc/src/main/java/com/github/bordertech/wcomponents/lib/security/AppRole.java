package com.github.bordertech.wcomponents.lib.security;

import java.io.Serializable;
import java.util.Set;

/**
 * Application roles that can be assigned to users and linked to servlet paths.
 *
 * @author Jonathan Austin
 */
public interface AppRole extends Serializable {

	String getRoleName();

	Set<AppPath> getAppPaths();

}
