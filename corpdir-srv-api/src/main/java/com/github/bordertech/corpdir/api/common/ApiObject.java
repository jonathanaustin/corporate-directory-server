package com.github.bordertech.corpdir.api.common;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * API Object common fields.
 *
 * @author jonathan
 */
public interface ApiObject extends Serializable {

	public String getId();

	public void setId(final String id);

	public String getBusinessKey();

	public void setBusinessKey(final String businessKey);

	public Timestamp getVersion();

	public void setVersion(final Timestamp version);

	public boolean isCustom();

	public void setCustom(final boolean custom);

	public boolean isActive();

	public void setActive(final boolean active);

}
