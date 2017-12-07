package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.wcomponents.lib.common.WLibMenuItem;

/**
 *
 * @author jonathan
 */
public class MenuItemSecurePath extends WLibMenuItem {

	private final AppPath appPath;

	public MenuItemSecurePath(final String text, final String path) {
		this(text, new DefaultAppPath(path));
	}

	public MenuItemSecurePath(final String text, final AppPath path) {
		super(text, path.getPath());
		this.appPath = path;
	}

	public AppPath getAppPath() {
		return appPath;
	}

	public void setSecureMode(final boolean secureMode) {
		getOrCreateComponentModel().secureMode = secureMode;
	}

	public boolean isSecureMode() {
		return getComponentModel().secureMode;
	}

	@Override
	public boolean isVisible() {
		return super.isVisible() && checkAccess();
	}

	protected boolean checkAccess() {
		if (!isSecureMode()) {
			return true;
		}
		return AppManagerFactory.getInstance().userAccessToPath(appPath);
	}

	@Override
	protected SecureMenuItemModel newComponentModel() {
		return new SecureMenuItemModel();
	}

	@Override
	protected SecureMenuItemModel getOrCreateComponentModel() {
		return (SecureMenuItemModel) super.getOrCreateComponentModel();
	}

	@Override
	protected SecureMenuItemModel getComponentModel() {
		return (SecureMenuItemModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 */
	public static final class SecureMenuItemModel extends MenuItemModel {

		/**
		 * Set true if using security
		 */
		private boolean secureMode;

	}

}
