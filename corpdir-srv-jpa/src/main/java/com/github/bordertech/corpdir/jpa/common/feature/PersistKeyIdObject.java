package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Persistent Keyed object required fields.
 *
 * @author jonathan
 */
public interface PersistKeyIdObject extends PersistObject, PersistTimestamp {

	Long getId();

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

	String getDescription();

	void setDescription(final String description);

	boolean isActive();

	void setActive(final boolean active);

	boolean isCustom();

	void setCustom(final boolean custom);

}
