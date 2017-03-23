package com.github.bordertech.corpdir.api.data;

import java.io.Serializable;
import java.util.Date;

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

	public Date getVersion();

	public void setVersion(final Date version);

	public boolean isCustom();

	public void setCustom(final boolean custom);

	public boolean isActive();

	public void setActive(final boolean active);

}
