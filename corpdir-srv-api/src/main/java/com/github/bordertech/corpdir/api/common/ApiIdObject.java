package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;

/**
 * API Keyed object common fields.
 *
 * @author jonathan
 */
public interface ApiIdObject extends ApiObject {

	String ID_PREFIX = "_";

	String TEMP_NEW_ID_PREFIX = "_TEMP_";

	String getId();

	String getDescription();

	void setDescription(final String description);

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
