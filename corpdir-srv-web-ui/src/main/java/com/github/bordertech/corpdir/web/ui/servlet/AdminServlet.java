package com.github.bordertech.corpdir.web.ui.servlet;

import com.github.bordertech.corpdir.web.ui.CorpDirApp;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.lib.security.WLibServlet;
import com.github.bordertech.wcomponents.registry.UIRegistry;

/**
 * Admin UI Servlet.
 *
 * @author jonathan
 */
public class AdminServlet extends WLibServlet {

	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(CorpDirApp.class.getName());
	}

}
