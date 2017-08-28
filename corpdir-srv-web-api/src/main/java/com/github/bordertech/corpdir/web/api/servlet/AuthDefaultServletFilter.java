package com.github.bordertech.corpdir.web.api.servlet;

import com.github.bordertech.corpdir.security.Role;
import com.github.bordertech.corpdir.security.User;
import com.github.bordertech.corpdir.security.UserHelper;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.container.PreMatching;

/**
 * Setup the user details for authentication.
 *
 * @author jonathan
 */
@PreMatching
public class AuthDefaultServletFilter implements Filter {

	private static final User USER = new User("default", "default user", Arrays.asList(Role.USER, Role.ADMIN, Role.SYSTEM_ADMIN));

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		UserHelper.setCurrentUser(getUser());
		try {
			chain.doFilter(request, response);
		} finally {
			UserHelper.clearCurrentUser();
		}
	}

	@Override
	public void destroy() {
		// Do Nothing
	}

	protected User getUser() {
		return USER;
	}

}
