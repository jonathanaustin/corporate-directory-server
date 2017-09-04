package com.github.bordertech.corpdir.api.common;

/**
 * Default Keyed API Object with version data.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DefaultVersionObject extends DefaultKeyIdObject implements ApiVersionable {

	private Long versionId;

	protected DefaultVersionObject() {
	}

	public DefaultVersionObject(final String id) {
		super(id);
	}

	@Override
	public Long getVersionId() {
		return versionId;
	}

	@Override
	public void setVersionId(final Long versionId) {
		this.versionId = versionId;
	}

}
