package com.github.bordertech.corpdir.api.common;

import java.sql.Timestamp;

/**
 * API Keyed object common fields.
 *
 * @author jonathan
 */
public interface ApiKeyIdObject extends ApiObject {

	public String getId();

	public void setId(final String id);

	public String getBusinessKey();

	public void setBusinessKey(final String businessKey);

	public String getDescription();

	public void setDescription(final String description);

	public Timestamp getVersion();

	public void setVersion(final Timestamp version);

	public boolean isCustom();

	public void setCustom(final boolean custom);

	public boolean isActive();

	public void setActive(final boolean active);

}
