package com.github.bordertech.corpdir.security;

/**
 * Type of user role.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public enum Role {

	/**
	 * Basic user.
	 */
	USER("user"),
	/**
	 * Contact Admin.
	 */
	ADMIN("admin"),
	/**
	 * System Admin.
	 */
	SYSTEM_ADMIN("systemadmin");

	private final String value;

	/**
	 *
	 * @param type the role type
	 */
	Role(final String type) {
		value = type;
	}

	/**
	 *
	 * @return the type value
	 */
	public String value() {
		return value;
	}

	/**
	 *
	 * @param value the type value
	 * @return the role type
	 */
	public static Role fromValue(final String value) {
		for (Role c : Role.values()) {
			if (c.value.equals(value)) {
				return c;
			}
		}
		return null;
	}

}
