package com.github.bordertech.wcomponents.lib.resource;

import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.lib.resource.ApplicationResourceWContent;
import com.github.bordertech.wcomponents.lib.resource.RegisterWcLibJsResource;
import com.github.bordertech.wcomponents.lib.resource.TemplateWContent;

/**
 *
 * @author jonathan
 */
public class ConfigLibUtil {

	private ConfigLibUtil() {
	}

	/**
	 * Configure an application to use wc library.
	 *
	 * @param app the application to configure
	 */
	public static void configApplication(final WApplication app) {

		// CSS
		app.addCssUrl("wclib/css/lib/cssgrid@0.0.4.css");

		// Javascript - Allow requireJS to load wclib js libraries
		TemplateWContent registerWclib = new TemplateWContent(new RegisterWcLibJsResource(), "reg");
		app.add(registerWclib);
		app.addJsResource(new ApplicationResourceWContent(registerWclib, "regkey"));
	}

}
