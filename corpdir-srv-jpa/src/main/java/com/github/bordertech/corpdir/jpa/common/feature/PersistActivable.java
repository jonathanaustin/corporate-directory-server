package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Persistent object with Active flags.
 *
 * @author jonathan
 */
public interface PersistActivable extends PersistIdObject {

	boolean isActive();

	void setActive(final boolean active);

}
