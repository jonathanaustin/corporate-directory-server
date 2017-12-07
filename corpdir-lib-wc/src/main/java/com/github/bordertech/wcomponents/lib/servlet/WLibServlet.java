package com.github.bordertech.wcomponents.lib.servlet;

import com.github.bordertech.wcomponents.Environment;
import com.github.bordertech.wcomponents.UserAgentInfo;
import com.github.bordertech.wcomponents.servlet.SubSessionHttpServletRequestWrapper;
import com.github.bordertech.wcomponents.servlet.WServlet;
import com.github.bordertech.wcomponents.util.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Setup the {@link EnvironmentHelper} and derive the theme path using the base URL.
 *
 * @author Jonathan Austin
 */
public class WLibServlet extends WServlet {

	/**
	 * Config flag to use the base URL on the theme URL.
	 */
	private static final boolean THEME_USE_BASEURL = Config.getInstance().getBoolean("wclib.theme.use.baseurl", true);

	@Override
	protected void serviceInt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			WLibServletHelper helper = new WLibServletHelper(this, request, response);
			Environment env = helper.createEnvironment();
			EnvironmentHelper.setEnvironment(env);
			super.serviceInt(request, response);
		} finally {
			EnvironmentHelper.clearDetails();
		}
	}

	@Override
	protected WServletHelper createServletHelper(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		return new WLibServletHelper(this, httpServletRequest, httpServletResponse);
	}

	/**
	 * Extend to make create environment public.
	 */
	public static class WLibServletHelper extends WServlet.WServletHelper {

		public WLibServletHelper(final WServlet servlet, final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
			super(servlet, httpServletRequest, httpServletResponse);
		}

		@Override
		protected Environment createEnvironment() {
			HttpServletRequest request = getBackingRequest();
			String postPath = getResponseUrl(request);
			String baseUrl = getBaseUrl(request);
			String userAgent = request.getHeader("user-agent");

			/**
			 * Careful - this won't be serializable
			 */
			WLibServletEnvironment env = new WLibServletEnvironment(postPath, baseUrl, userAgent);
			if (request instanceof SubSessionHttpServletRequestWrapper) {
				env.setSubsessionId(((SubSessionHttpServletRequestWrapper) request).getSessionId());
			}

			return env;
		}
	}

	/**
	 * Extend to put the base URL on the theme URL.
	 */
	public static class WLibServletEnvironment extends WServlet.WServletEnvironment {

		public WLibServletEnvironment(final String postPath, final String baseUrl, final String userAgent) {
			super(postPath, baseUrl, userAgent);
		}

		public WLibServletEnvironment(final String postPath, final String baseUrl, final UserAgentInfo userAgentInfo) {
			super(postPath, baseUrl, userAgentInfo);
		}

		@Override
		public String getThemePath() {
			String url = super.getThemePath();
			if (THEME_USE_BASEURL) {
				return EnvironmentHelper.prefixBaseUrl(url);
			}
			return url;
		}
	}

}
