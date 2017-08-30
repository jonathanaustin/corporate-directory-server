package com.github.bordertech.corpdir.jpa.common.feature;

/**
 * Keyed API Object with a Version.
 *
 * @author jonathan
 * @param <T> the owner type
 */
public interface PersistentVersionable<T extends PersistentVersionData> extends PersistentIdentifiable {

	VersionIdKey getVersionIdKey();

	Integer getVersionId();

	T getItem();

}
