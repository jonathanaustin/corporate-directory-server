package com.github.bordertech.corpdir.jpa.common.feature;

import com.github.bordertech.corpdir.jpa.common.version.ItemTreeVersion;

/**
 * Persistent Object that can have versioned data.
 *
 * @author jonathan
 * @param <T> the persistent type
 * @param <V> the version type
 */
public interface PersistVersionableKeyIdTree<T extends PersistVersionableKeyIdTree<T, V>, V extends ItemTreeVersion<T, V>> extends PersistVersionableKeyId<T, V> {

}
