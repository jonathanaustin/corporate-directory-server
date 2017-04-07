package com.github.bordertech.corpdir.jpa.common;

import java.sql.Timestamp;

/**
 * Persistent Keyed object required fields.
 *
 * @author jonathan
 */
public interface PersistentKeyIdObject extends PersistentObject {

	public Long getId();

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
