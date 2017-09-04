package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Persistent object with Business Key and required fields.
 *
 * @author jonathan
 */
public interface PersistKeyIdObject extends PersistIdObject {

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

	boolean isActive();

	void setActive(final boolean active);

	boolean isCustom();

	void setCustom(final boolean custom);

}
