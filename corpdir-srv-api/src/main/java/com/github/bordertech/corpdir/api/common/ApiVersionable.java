package com.github.bordertech.corpdir.api.common;

/**
 * API Object with versioned data.
 *
 * @author jonathan
 */
public interface ApiVersionable extends ApiKeyIdObject {

	Integer getVersionId();

	void setVersionId(final Integer versionId);

}
