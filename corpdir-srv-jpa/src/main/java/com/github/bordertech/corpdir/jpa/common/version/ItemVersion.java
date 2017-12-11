package com.github.bordertech.corpdir.jpa.common.version;

import com.github.bordertech.corpdir.jpa.common.feature.PersistIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;

/**
 * Versionable data.
 *
 * @param <T> the version data owner type
 * @author jonathan
 */
public interface ItemVersion<T extends PersistVersionableKeyId> extends PersistIdObject {

	VersionKey getVersionKey();

	Long getVersionId();

	T getItem();

	VersionCtrlEntity getVersionCtrl();

}
