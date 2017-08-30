package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Object has versioned data.
 *
 * @author jonathan
 * @param <T>
 */
public interface PersistentVersionData<T extends PersistentVersionable> extends PersistentIdentifiable {

	Set<T> getDataVersions();

	void addDataVersion(final T versionData);

	void removeDataVersion(final Integer versionId);

	T getDataVersion(final Integer versionId);

}
