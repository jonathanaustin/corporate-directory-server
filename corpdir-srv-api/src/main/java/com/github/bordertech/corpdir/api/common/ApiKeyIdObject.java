package com.github.bordertech.corpdir.api.common;

/**
 * API Keyed object common fields.
 *
 * @author jonathan
 */
public interface ApiKeyIdObject extends ApiIdentifiable {

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

	String getDescription();

	void setDescription(final String description);

	boolean isCustom();

	void setCustom(final boolean custom);

	boolean isActive();

	void setActive(final boolean active);

}
