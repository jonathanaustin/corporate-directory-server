package com.github.bordertech.corpdir.jpa.common.feature;

import java.sql.Timestamp;

/**
 * Persistent object with an ID.
 *
 * @author jonathan
 */
public interface PersistIdObject extends PersistObject {

	Long getId();

	String getDescription();

	void setDescription(final String description);

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
