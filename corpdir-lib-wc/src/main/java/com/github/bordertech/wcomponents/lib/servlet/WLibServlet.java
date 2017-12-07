package com.github.bordertech.wcomponents.lib.servlet;

import com.github.bordertech.wcomponents.Environment;
import com.github.bordertech.wcomponents.servlet.HttpServletHelper;
import com.github.bordertech.wcomponents.servlet.WServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jonathan
 */
public class WLibServlet extends WServlet {

	@Override
	protected void serviceInt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			MyServletHelper helper = new MyServletHelper(this, request, response);
			Environment env = helper.createEnvironment();
			EnvironmentHelper.setEnvironment(env);
			super.serviceInt(request, response);
		} finally {
			EnvironmentHelper.clearDetails();
		}
	}

	/**
	 * Extends Helper to make CreateEnvironment public.
	 */
	private static class MyServletHelper extends HttpServletHelper {

		public MyServletHelper(final HttpServlet servlet, final HttpServletRequest httpServletRequest,
				final HttpServletResponse httpServletResponse) {
			super(servlet, httpServletRequest, httpServletResponse);
		}

		@Override
		public Environment createEnvironment() {
			return super.createEnvironment();
		}
	}

}
