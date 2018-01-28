package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Persistent object with custom flags.
 *
 * @author jonathan
 */
public interface PersistCustomable extends PersistIdObject {

	boolean isCustom();

	void setCustom(final boolean custom);

}
