package com.github.bordertech.corpdir.jpa.common.feature;

import java.sql.Timestamp;

/**
 * Persistent Keyed object required fields.
 *
 * @author jonathan
 */
public interface PersistTimestamp extends PersistObject {

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
