package com.github.bordertech.corpdir.api.common;

/**
 * Abstract Keyed API Object with a Tree Structure.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultTreeVersionObject extends DefaultTreeObject implements ApiVersionable {

	private Integer versionId;

	@Override
	public Integer getVersionId() {
		return versionId;
	}

	@Override
	public void setVersionId(final Integer versionId) {
		this.versionId = versionId;
	}

}
