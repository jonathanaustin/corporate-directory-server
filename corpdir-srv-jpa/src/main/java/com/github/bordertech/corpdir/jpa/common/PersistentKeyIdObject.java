package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistentIdentifiable;

/**
 * Persistent Keyed object required fields.
 *
 * @author jonathan
 */
public interface PersistentKeyIdObject extends PersistentIdentifiable {

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

	String getDescription();

	void setDescription(final String description);

	boolean isActive();

	void setActive(final boolean active);

	boolean isCustom();

	void setCustom(final boolean custom);

}
