package com.github.bordertech.wcomponents.lib.servlet;

import com.github.bordertech.wcomponents.Environment;

/**
 * Environment helper to make the environment details available on the thread.
 *
 * @author jonathan
 */
public final class EnvironmentHelper {

	private static final ThreadLocal<Environment> THREAD_LOCAL_ENVIRONMENT = new ThreadLocal<>();

	public static void setEnvironment(final Environment env) {
		THREAD_LOCAL_ENVIRONMENT.set(env);
	}

	public static String getBaseUrl() {
		Environment env = THREAD_LOCAL_ENVIRONMENT.get();
		return env == null ? "" : env.getBaseUrl();
	}

	public static String prefixBaseUrl(final String relativeUrl) {
		return prefixUrl(getBaseUrl(), relativeUrl);
	}

	private static String prefixUrl(final String prefix, final String relativeUrl) {
		if (relativeUrl == null) {
			throw new IllegalArgumentException("Relative URL cannot be null");
		}
		if (prefix == null || prefix.isEmpty()) {
			return relativeUrl;
		}
		StringBuilder result = new StringBuilder();
		result.append(prefix);
		if (!relativeUrl.startsWith("/")) {
			result.append("/");
		}
		result.append(relativeUrl);
		return result.toString();
	}

	/**
	 * Clear the details of the current AJAX operation on the thread.
	 */
	public static void clearDetails() {
		THREAD_LOCAL_ENVIRONMENT.remove();
	}

}
