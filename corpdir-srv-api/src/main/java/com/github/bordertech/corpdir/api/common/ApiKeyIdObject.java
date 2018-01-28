package com.github.bordertech.corpdir.api.common;

/**
 * API Keyed object common fields.
 *
 * @author jonathan
 */
public interface ApiKeyIdObject extends ApiIdObject, ApiActivable, ApiCustomable {

	String getBusinessKey();

	void setBusinessKey(final String businessKey);

}
