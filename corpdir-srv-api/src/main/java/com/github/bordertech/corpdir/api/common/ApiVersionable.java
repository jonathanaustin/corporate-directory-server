package com.github.bordertech.corpdir.api.common;

/**
 * API Object uses a version id.
 *
 * @author jonathan
 */
public interface ApiVersionable extends ApiIdentifiable {

	Integer getVersionId();

	void setVersionId(final Integer versionId);

}
