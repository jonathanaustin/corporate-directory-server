package com.github.bordertech.corpdir.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Corporate Directory User.
 *
 * @author jonathan
 */
public class User implements Principal, Serializable {

	private final String userId;
	private final String name;
	private final List<Role> roles;

	public User(final String userId, final String name, final List<Role> roles) {
		this.userId = userId;
		this.name = name;
		this.roles = roles == null ? Collections.EMPTY_LIST : roles;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public boolean isUserInRole(final Role role) {
		return roles.contains(role);
	}

	public boolean isUserInRole(final String role) {
		Role type = Role.fromValue(role);
		if (type == null) {
			return false;
		}
		return isUserInRole(type);
	}

	@Override
	public int hashCode() {
		return userId.hashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof User && Objects.equals(getUserId(), ((User) obj).getUserId());
	}

}
