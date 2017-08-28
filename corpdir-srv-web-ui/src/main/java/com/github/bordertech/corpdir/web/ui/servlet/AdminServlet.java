package com.github.bordertech.corpdir.web.ui.servlet;

import com.github.bordertech.corpdir.web.ui.CorpDirApp;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import com.github.bordertech.wcomponents.servlet.WServlet;

/**
 * Admin UI Servlet.
 *
 * @author jonathan
 */
public class AdminServlet extends WServlet {

	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(CorpDirApp.class.getName());
	}

}
