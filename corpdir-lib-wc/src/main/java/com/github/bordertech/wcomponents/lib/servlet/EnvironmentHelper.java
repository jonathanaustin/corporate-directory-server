package com.github.bordertech.wcomponents.lib.servlet;

import com.github.bordertech.wcomponents.Environment;
import com.github.bordertech.wcomponents.util.Config;

/**
 * Environment helper to make the environment details available on the thread.
 *
 * @author jonathan
 */
public final class EnvironmentHelper {

	private static final String SECURE_SERVLET_PATH = Config.getInstance().getString("wclib.secure.cardmgr.servlet.path", "/");

	private static final ThreadLocal<Environment> THREAD_LOCAL_ENVIRONMENT = new ThreadLocal<>();

	public static void setEnvironment(final Environment env) {
		THREAD_LOCAL_ENVIRONMENT.set(env);
	}

	/**
	 * Get the base url at which this web application is hosted.
	 * <p>
	 * Implementations ensure that this method returns a URL WITHOUT a trailing slash, as in above example.
	 * </p>
	 *
	 * @return the base url at which this web application is hosted.
	 */
	public static String getBaseUrl() {
		Environment env = THREAD_LOCAL_ENVIRONMENT.get();
		return env == null ? "" : env.getBaseUrl();
	}

	public static String prefixBaseUrl(final String relativeUrl) {
		return prefixUrl(getBaseUrl(), relativeUrl);
	}

	/**
	 * Get the path of the secure servlet.
	 * <p>
	 * Implementations ensure that this method returns a URL WITH a trailing slash.
	 * </p>
	 *
	 * @param relativeUrl the relative url
	 * @return the secure servlet path at which this web application is hosted.
	 */
	public static String getSecureServletPath() {
		return SECURE_SERVLET_PATH;
	}

	public static String prefixSecureServletPath(final String relativeUrl) {
		return prefixUrl(getSecureServletPath(), relativeUrl);
	}

	private static String prefixUrl(final String prefix, final String relativeUrl) {
		if (prefix == null) {
			throw new IllegalArgumentException("Prefix cannot be null");
		}
		if (relativeUrl == null) {
			throw new IllegalArgumentException("Relative URL cannot be null");
		}

		boolean prefixHas = prefix.endsWith("/");
		boolean relativeHas = relativeUrl.startsWith("/");

		StringBuilder result = new StringBuilder();
		result.append(prefix);
		// Both have a dash
		if (prefixHas && relativeHas) {
			// Remove dash
			result.append(relativeUrl.substring(1));
		} else if (!prefixHas && !relativeHas) {
			// Add a dash
			result.append("/");
			result.append(relativeUrl);
		} else {
			result.append(relativeUrl);
		}
		return result.toString();
	}

	/**
	 * Clear the details of the current AJAX operation on the thread.
	 */
	public static void clearDetails() {
		THREAD_LOCAL_ENVIRONMENT.remove();
	}

}
