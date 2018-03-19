package com.github.bordertech.corpdir.web.ui.servlet;

import com.github.bordertech.config.Config;
import com.github.bordertech.corpdir.web.ui.CorpDirApp;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.addons.common.relative.WLibServlet;
import com.github.bordertech.wcomponents.registry.UIRegistry;
import org.apache.commons.configuration.Configuration;

/**
 * Admin UI Servlet.
 *
 * @author jonathan
 */
public class AdminServlet extends WLibServlet {

	static {
		// Combine bordertech config and WC
		Configuration bt = Config.getInstance();
		com.github.bordertech.wcomponents.util.Config.setConfiguration(bt);
	}

	@Override
	public WComponent getUI(final Object httpServletRequest) {
		return UIRegistry.getInstance().getUI(CorpDirApp.class.getName());
	}

}
