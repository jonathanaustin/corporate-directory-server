package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Persistent object with Business Key and required fields.
 *
 * @author jonathan
 */
public interface PersistKeyIdObject extends PersistIdObject, PersistActivable, PersistCustomable {

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

}
