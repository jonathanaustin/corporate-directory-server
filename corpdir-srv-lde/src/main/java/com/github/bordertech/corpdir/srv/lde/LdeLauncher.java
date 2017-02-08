package com.github.bordertech.corpdir.srv.lde;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Start Jetty Server.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class LdeLauncher {

	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setResourceBase("../corpdir-srv-web-war/src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);

		server.setHandler(context);

		server.start();
		server.join();
	}

}
