package com.github.bordertech.corpdir.srv.lde;

import java.util.HashSet;
import java.util.Set;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import static org.eclipse.jetty.webapp.MetaInfConfiguration.METAINF_RESOURCES;
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

		// Override the config to use the custom Meta-Inf Config 9to pick up the wclib-lib resources)
		context.setConfigurations(new Configuration[]{new WebInfConfiguration(), new WebXmlConfiguration(), new MyMetaInfConfig()});

		server.setHandler(context);
		server.start();
		server.join();
	}

	/**
	 * Loads META-INF/resources (ie webjars) from the classpath which are not in the "jars" jetty looks for on startup.
	 * <p>
	 * This is only needed for "embedded" jetty.
	 * </p>
	 */
	public static class MyMetaInfConfig extends MetaInfConfiguration {

		@Override
		public void preConfigure(final WebAppContext context) throws Exception {
			super.preConfigure(context);
			handleClassPathMetaInf(context);
		}

		/**
		 * Check for any META-INF/resources on the classpath.
		 *
		 * @param context the Web App Context
		 */
		public void handleClassPathMetaInf(final WebAppContext context) {
			Resource metainf = Resource.newClassPathResource("META-INF/resources");
			if (metainf == null) {
				return;
			}
			//add it to the meta inf resources for this context
			Set<Resource> dirs = (Set<Resource>) context.getAttribute(METAINF_RESOURCES);
			if (dirs == null) {
				dirs = new HashSet<>();
				context.setAttribute(METAINF_RESOURCES, dirs);
			}
			dirs.add(metainf);
		}

	}

}
