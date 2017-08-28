package com.github.bordertech.corpdir.security;

/**
 * Holds the User Security details.
 *
 * @author jonathan
 */
public final class UserHelper {

	private static final ThreadLocal<User> THREAD_LOCAL_USER = new ThreadLocal<>();

	/**
	 * Prevent instantiation of this class.
	 */
	private UserHelper() {
	}

	/**
	 * Set the current user on the Thread.
	 *
	 * @param user the current user details
	 */
	public static void setCurrentUser(final User user) {
		if (user == null) {
			THREAD_LOCAL_USER.remove();
		} else {
			THREAD_LOCAL_USER.set(user);
		}
	}

	/**
	 * @return the current USER (if any).
	 */
	public static User getCurrentUser() {
		return THREAD_LOCAL_USER.get();
	}

	/**
	 * Clear the details of the current USER on the thread.
	 */
	public static void clearCurrentUser() {
		THREAD_LOCAL_USER.remove();
	}

}
