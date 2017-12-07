package com.github.bordertech.wcomponents.lib.security;

import com.github.bordertech.wcomponents.Environment;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.UIContext;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.WCardManager;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.servlet.EnvironmentHelper;
import com.github.bordertech.wcomponents.lib.servlet.WLibServlet;
import com.github.bordertech.wcomponents.util.Config;
import com.github.bordertech.wcomponents.util.SystemException;
import com.github.bordertech.wcomponents.util.Util;
import com.google.common.base.Objects;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Secure Card Manager and Path.
 * <p>
 * Use {@link WLibServlet} to setup the {@link EnvironmentHelper}.
 * </p>
 *
 * @author Jonathan Austin
 * @param <T> the card type
 */
public class SecureCardManager<T extends WComponent> extends WCardManager {

	private static final String SERVLET_PATH = Config.getInstance().getString("wclib.secure.cardmgr.servlet.path", "/");

	@Override
	public void handleRequest(final Request request) {
		super.handleRequest(request);

		checkUrlPath();
		checkAccess();

		// Get environment details
		UIContext uic = UIContextHolder.getCurrent();
		uic = UIContextHolder.getPrimaryUIContext(uic);
		final Environment env = uic.getEnvironment();

		// Add an action to run at the end of all the actions to set the post path to reflect the active card.
		// We need to run this last incase any other actions change the active card.
		uic.invokeLater(new Runnable() {
			@Override
			public void run() {
				String currentScreenPath = getCurrentScreenPath(env.getPostPath());
				AppPath destinationPath = getPathForCard(getVisible());
				if (destinationPath != null
						&& destinationPath.getPath() != null
						&& !Util.equals(currentScreenPath, destinationPath.getPath())) {
					String url = getRedirectUrl(destinationPath);
					forward(url);
				}
			}
		});
	}

	protected String getRedirectUrl(final AppPath path) {
		return EnvironmentHelper.prefixBaseUrl(SERVLET_PATH + path.getPath());
	}

	public void setSecureMode(final boolean secureMode) {
		getOrCreateComponentModel().secureMode = secureMode;
	}

	public boolean isSecureMode() {
		return getComponentModel().secureMode;
	}

	@Override
	public void add(final WComponent component) {
		throw new IllegalStateException("Component must be added with a path");
	}

	@Override
	public void add(final WComponent component, String tag) {
		throw new IllegalStateException("Component must be added with a path");
	}

	/**
	 * @param component the card to add
	 * @param path the matching URL for the card
	 */
	public void addWithPath(final WComponent component, final String path) {
		addWithPath(component, new DefaultAppPath(path));
	}

	/**
	 * @param component the card to add
	 * @param path the matching AppPath for the card
	 */
	public void addWithPath(final WComponent component, final AppPath path) {
		super.add(component);
		addCardPath(component, path);
	}

	@Override
	public void remove(final WComponent component) {
		super.remove(component);
		removeCardPath(component);
	}

	/**
	 * @return the path of the current card
	 */
	public AppPath getCurrentPath() {
		return getPathForCard(getVisible());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void makeVisible(final WComponent component) {
		super.makeVisible(component);
		checkAccess();
	}

	public List<T> getCards() {
		return (List<T>) getCardContainer().getChildren();
	}

	/**
	 * Check the visible card and the URL path match.
	 */
	protected void checkUrlPath() {
		// Get environment details
		UIContext uic = UIContextHolder.getCurrent();
		uic = UIContextHolder.getPrimaryUIContext(uic);
		final Environment env = uic.getEnvironment();

		// Make sure that the correct card is visible based on the URL.
		String currentScreenPath = getCurrentScreenPath(env.getPostPath());
		// Match Path to Card
		WComponent screen = getCardForScreenPath(currentScreenPath);
		if (screen != null && getVisible() != screen) {
			super.makeVisible(screen);
		}
	}

	/**
	 * Check if the user is allowed to access this card.
	 */
	protected void checkAccess() {
		getVisible().setVisible(true);
		if (isSecureMode()) {
			AppPath path = getCurrentPath();
			boolean allowed = AppManagerFactory.getInstance().userAccessToPath(path);
			if (!allowed) {
				getVisible().setVisible(false);
				handleAccessError();
			}
		}
	}

	protected void handleAccessError() {
		throw new SystemException("You don't have access to this system.");
	}

	protected String getCurrentScreenPath(final String postPath) {
		int idx = postPath.lastIndexOf(SERVLET_PATH) + SERVLET_PATH.length();
		String currentScreenPath = postPath.substring(idx);
		return currentScreenPath;
	}

	/**
	 * @param screenPath the screen path
	 * @return the matching card
	 */
	protected WComponent getCardForScreenPath(final String screenPath) {
		SecureCardManagerModel model = getComponentModel();
		if (model.pathToComponent == null) {
			return null;
		}
		for (Map.Entry<AppPath, WComponent> entry : model.pathToComponent.entrySet()) {
			AppPath path = entry.getKey();
			if (Objects.equal(path.getPath(), screenPath)) {
				return entry.getValue();
			}
		}
		return null;
	}

	/**
	 * @param component the card to find
	 * @return the appPath for the card
	 */
	protected AppPath getPathForCard(final WComponent component) {
		SecureCardManagerModel model = getComponentModel();
		if (model.pathToComponent == null) {
			return null;
		}
		for (Map.Entry<AppPath, WComponent> entry : model.pathToComponent.entrySet()) {
			if (component == entry.getValue()) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * @param component the card to add
	 * @param path the matching URL for the card
	 */
	protected void addCardPath(final WComponent component, final AppPath path) {
		SecureCardManagerModel model = getOrCreateComponentModel();
		if (model.pathToComponent == null) {
			model.pathToComponent = new HashMap<>();
		}
		model.pathToComponent.put(path, component);
	}

	/**
	 *
	 * @param component the card to remove
	 */
	protected void removeCardPath(final WComponent component) {
		SecureCardManagerModel model = getOrCreateComponentModel();
		if (model.pathToComponent == null) {
			return;
		}
		for (Map.Entry<AppPath, WComponent> entry : model.pathToComponent.entrySet()) {
			if (component == entry.getValue()) {
				model.pathToComponent.remove(entry.getKey());
				if (model.pathToComponent.isEmpty()) {
					model.pathToComponent = null;
				}
				break;
			}
		}
	}

	@Override
	protected SecureCardManagerModel newComponentModel() {
		return new SecureCardManagerModel();
	}

	@Override
	protected SecureCardManagerModel getOrCreateComponentModel() {
		return (SecureCardManagerModel) super.getOrCreateComponentModel();
	}

	@Override
	protected SecureCardManagerModel getComponentModel() {
		return (SecureCardManagerModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 */
	public static final class SecureCardManagerModel extends CardManagerModel {

		/**
		 * Map path to component.
		 */
		private Map<AppPath, WComponent> pathToComponent;

		/**
		 * Set true if using security
		 */
		private boolean secureMode;

	}

}
