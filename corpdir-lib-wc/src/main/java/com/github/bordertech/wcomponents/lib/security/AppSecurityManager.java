package com.github.bordertech.wcomponents.lib.security;

import java.io.Serializable;
import java.util.Set;

/**
 * App manager is responsible to determine a users access.
 *
 * @author Jonathan Austin
 */
public interface AppSecurityManager extends Serializable {

	Set<AppRole> getUserRoles();

	boolean userAccessToRole(final AppRole role);

	boolean userAccessToPath(final AppPath role);

	Set<AppPath> getAppPaths();

	Set<AppRole> getAppRoles();

	AppPath findAppPath(final String path);

}
