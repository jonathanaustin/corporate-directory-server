package com.github.bordertech.corpdir.api.common;

/**
 * API Object with versioned data.
 *
 * @author jonathan
 */
public interface ApiVersionable extends ApiKeyIdObject {

	Long getVersionId();

	void setVersionId(final Long versionId);

}
