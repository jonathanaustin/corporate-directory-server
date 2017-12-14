package com.github.bordertech.corpdir.jpa.common.feature;

import com.github.bordertech.corpdir.jpa.common.version.ItemVersion;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.Set;

/**
 * Persistent Object that can have versioned data.
 *
 * @author jonathan
 * @param <T> the persistent type
 * @param <V> the version type
 */
public interface PersistVersionableKeyId<T extends PersistVersionableKeyId<T, V>, V extends ItemVersion<T>> extends PersistKeyIdObject {

	Set<V> getVersions();

	void addVersion(final V version);

	void removeVersion(final Long versionId);

	V getVersion(final Long versionId);

	V getOrCreateVersion(final VersionCtrlEntity ctrl);

	V createVersion(final VersionCtrlEntity ctrl);

}
