package com.github.bordertech.corpdir.api.common;

/**
 * Default Keyed API Object with version data.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultVersionObject extends DefaultObject implements ApiVersionable {

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
