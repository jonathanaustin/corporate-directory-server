package com.github.bordertech.corpdir.api.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Keyed API Object with a Tree Structure.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultTreeObject extends DefaultKeyIdObject implements ApiTreeable {

	private String parentId;
	private List<String> subIds;

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public void setParentId(final String parentId) {
		this.parentId = parentId;
	}

	@Override
	public List<String> getSubIds() {
		if (subIds == null) {
			subIds = new ArrayList<>();
		}
		return subIds;
	}

	@Override
	public void setSubIds(final List<String> subIds) {
		this.subIds = subIds;
	}
}
