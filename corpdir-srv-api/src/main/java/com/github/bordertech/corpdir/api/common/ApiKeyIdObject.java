package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;

/**
 * API Keyed object common fields.
 *
 * @author jonathan
 */
public interface ApiKeyIdObject extends ApiObject {

	String getId();

	void setId(final String id);

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

	String getDescription();

	void setDescription(final String description);

	boolean isCustom();

	void setCustom(final boolean custom);

	boolean isActive();

	void setActive(final boolean active);

	Timestamp getTimestamp();

	void setTimestamp(final Timestamp timestamp);

}
