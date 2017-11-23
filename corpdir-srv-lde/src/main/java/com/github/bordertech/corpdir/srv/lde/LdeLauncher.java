package com.github.bordertech.corpdir.srv.lde;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

/**
 * Start Jetty Server.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LdeLauncher {

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

		// Application
		WebAppContext context = new WebAppContext();
		context.setResourceBase("../corpdir-srv-web-war/src/main/webapp");

		context.setContextPath("/");
		context.setParentLoaderPriority(true);

		// Override the config to use the custom Meta-Inf Config to pick up the wclib-lib resources)
		context.setConfigurations(new Configuration[]{new WebInfConfiguration(), new WebXmlConfiguration(), new LdeJettyEmbeddedMetaInfConfig()});

		server.setHandler(context);
		server.start();
		server.join();
	}

}
