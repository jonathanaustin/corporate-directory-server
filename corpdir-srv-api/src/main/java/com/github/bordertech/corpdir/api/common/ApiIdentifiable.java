package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;

/**
 * API Object with an ID.
 *
 * @author jonathan
 */
public interface ApiIdentifiable extends ApiObject {

	String getId();

	void setId(final String id);

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
