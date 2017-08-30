package com.github.bordertech.corpdir.api.common;

import java.util.List;

/**
 * API Object with a Tree Structure.
 *
 * @author jonathan
 */
public interface ApiTreeable extends ApiIdentifiable {

	String getParentId();

	void setParentId(final String parentId);

	List<String> getSubIds();

	void setSubIds(final List<String> subIds);

}
