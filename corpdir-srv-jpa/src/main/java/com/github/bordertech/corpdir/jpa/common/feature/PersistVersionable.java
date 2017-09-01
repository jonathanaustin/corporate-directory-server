package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Versionable data.
 *
 * @param <T> the version data owner type
 * @author jonathan
 */
public interface PersistVersionable<U extends PersistVersionable<U, T>, T extends PersistVersionData<U>> extends PersistObject, PersistTimestamp {

	VersionIdKey getVersionIdKey();

	Integer getVersionId();

	Long getId();

	T getItem();

}
