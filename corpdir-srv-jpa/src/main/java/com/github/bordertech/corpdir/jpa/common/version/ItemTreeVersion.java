package com.github.bordertech.corpdir.jpa.common.version;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import java.util.Set;

/**
 * Versionable data with a tree structure.
 *
 * @param <T> the version data owner type
 * @author jonathan
 */
public interface ItemTreeVersion<T extends PersistVersionableKeyId> extends ItemVersion<T> {

	void setParentItem(final T parentItem);

	void addChildItem(final T childItem);

	void removeChildItem(final T childItem);

	T getParentItem();

	Set<T> getChildrenItems();

}
