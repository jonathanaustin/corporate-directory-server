package com.github.bordertech.corpdir.web.api.servlet;

import com.github.bordertech.corpdir.security.User;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 * Provide the security context details of the user.
 *
 * @author jonathan
 */
public class AuthSecurityContext implements SecurityContext {

	private final User user;

	/**
	 * @param user the user details
	 */
	public AuthSecurityContext(final User user) {
		this.user = user;
	}

	@Override
	public Principal getUserPrincipal() {
		return user;
	}

	@Override
	public boolean isUserInRole(final String role) {
		return user.isUserInRole(role);
	}

	@Override
	public boolean isSecure() {
		// Can check scheme is https
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

}
