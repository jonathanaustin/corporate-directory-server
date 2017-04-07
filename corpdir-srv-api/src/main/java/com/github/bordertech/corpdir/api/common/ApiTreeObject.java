package com.github.bordertech.corpdir.api.common;

import java.util.List;

/**
 * API Tree object.
 *
 * @author jonathan
 */
public interface ApiTreeObject extends ApiKeyIdObject {

	String getParentId();

	void setParentId(final String parentId);

	List<String> getSubIds();

	void setSubIds(final List<String> subIds);

}
