package com.github.bordertech.swagger;

import io.swagger.config.SwaggerConfig;
import io.swagger.jaxrs.config.ReaderConfigUtils;
import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.models.Swagger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Sample config servlet. Based on io.swagger.jersey.config.JerseyJaxrsConfig.
 *
 * @author jonathan
 */
public class SwaggerServlet extends HttpServlet {

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);

		final String host = servletConfig.getInitParameter("api.host");
		String context = servletConfig.getServletContext().getContextPath();
		String api = servletConfig.getInitParameter("api.basepath");
		final String basePath = prefixUrl(context, api);
		SwaggerConfig config = new SwaggerConfig() {
			@Override
			public Swagger configure(final Swagger swagger) {
				swagger.setHost(host);
				swagger.setBasePath(basePath);
//				swagger.setSchemes(Arrays.asList(Scheme.HTTP, Scheme.HTTPS));
				return swagger;
			}

			@Override
			public String getFilterClass() {
				return null;
			}
		};
		new SwaggerContextService().withSwaggerConfig(config).initConfig().initScanner();
		ReaderConfigUtils.initReaderConfig(servletConfig);
	}

	protected String prefixUrl(final String prefix, final String relativeUrl) {
		boolean prefixHas = prefix.endsWith("/");
		boolean relativeHas = relativeUrl.startsWith("/");

		StringBuilder result = new StringBuilder();
		result.append(prefix);
		// Both have a dash
		if (prefixHas && relativeHas) {
			// Remove dash
			result.append(relativeUrl.substring(1));
		} else if (!prefixHas && !relativeHas) {
			// Add a dash
			result.append("/");
			result.append(relativeUrl);
		} else {
			result.append(relativeUrl);
		}
		return result.toString();
	}

}
