package com.github.bordertech.corpdir.api.common;

import java.util.List;

/**
 * API Nested pbject.
 *
 * @author jonathan
 */
public interface ApiNestedObject extends ApiKeyIdObject {

	String getParentId();

	void setParentId(final String parentId);

	List<String> getSubIds();

	void setSubIds(final List<String> subIds);

}
