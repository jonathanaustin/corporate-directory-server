package com.github.bordertech.swagger;

import io.swagger.jaxrs.config.ReaderConfigUtils;
import io.swagger.jaxrs.config.SwaggerContextService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * Sample config servlet.
 *
 * @author jonathan
 */
public class SwaggerServlet extends HttpServlet {

	@Override
	public void init(final ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		new SwaggerContextService().withServletConfig(servletConfig).initConfig().initScanner();
		ReaderConfigUtils.initReaderConfig(servletConfig);
	}

}
