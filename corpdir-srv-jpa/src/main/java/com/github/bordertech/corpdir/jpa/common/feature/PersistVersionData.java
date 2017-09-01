package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Object has versioned data.
 *
 * @author jonathan
 * @param <T>
 */
public interface PersistVersionData<T extends PersistVersionable> extends PersistKeyIdObject {

	Set<T> getDataVersions();

	void addDataVersion(final T versionData);

	void removeDataVersion(final Integer versionId);

	T getDataVersion(final Integer versionId);

}
