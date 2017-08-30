package com.github.bordertech.corpdir.jpa.common.feature;

import com.github.bordertech.corpdir.jpa.common.*;
import java.sql.Timestamp;

/**
 * Persistent Keyed object required fields.
 *
 * @author jonathan
 */
public interface PersistentIdentifiable extends PersistentObject {

	Long getId();

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
