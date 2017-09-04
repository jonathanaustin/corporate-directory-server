package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Versionable data with a tree structure.
 *
 * @param <U> the versionable tree data type
 * @param <T> the version data owner type
 * @author jonathan
 */
public interface PersistVersionableTree<U extends PersistVersionableTree<U, T>, T extends PersistVersionData<U>> extends PersistVersionable<U, T> {

	void setParentItem(final T parentItem);

	T getParentItem();

	Set<T> getChildrenItems();

	void addChildItem(final T childItem);

	void removeChildItem(final T childItem);

}
