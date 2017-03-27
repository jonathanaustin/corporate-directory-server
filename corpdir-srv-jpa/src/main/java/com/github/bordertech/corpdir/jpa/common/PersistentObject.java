package com.github.bordertech.corpdir.jpa.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Persistent Object required fields.
 *
 * @author jonathan
 */
public interface PersistentObject extends Serializable {

	public Long getId();

	public String getBusinessKey();

	public Timestamp getVersion();

	public void setVersion(final Timestamp version);

	public boolean isCustom();

	public void setCustom(final boolean custom);

	public boolean isActive();

	public void setActive(final boolean active);

}
