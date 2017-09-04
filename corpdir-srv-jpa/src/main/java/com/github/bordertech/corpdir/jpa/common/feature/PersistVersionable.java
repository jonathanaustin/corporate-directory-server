package com.github.bordertech.corpdir.jpa.common.feature;

import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;

/**
 * Versionable data.
 *
 * @param <U> the versionable data type
 * @param <T> the version data owner type
 * @author jonathan
 */
public interface PersistVersionable<U extends PersistVersionable<U, T>, T extends PersistVersionData<U>> extends PersistIdObject {

	VersionIdKey getVersionIdKey();

	Long getVersionId();

	T getItem();

	VersionCtrlEntity getVersionCtrl();

}
