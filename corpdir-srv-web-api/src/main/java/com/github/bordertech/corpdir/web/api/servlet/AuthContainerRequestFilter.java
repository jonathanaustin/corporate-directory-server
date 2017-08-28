package com.github.bordertech.corpdir.web.api.servlet;

import com.github.bordertech.corpdir.security.User;
import com.github.bordertech.corpdir.security.UserHelper;
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;

/**
 * Setup the user details for authentication.
 *
 * @author jonathan
 */
@PreMatching
public class AuthContainerRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		// Get the user details (if set on the Thread)
		User user = UserHelper.getCurrentUser();
		if (user != null) {
			requestContext.setSecurityContext(new AuthSecurityContext(user));
		}
	}

}
