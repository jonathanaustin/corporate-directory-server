package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Versiobnable data with a tree structure.
 *
 * @author jonathan
 * @param <T> the item id types
 */
public interface PersistVersionableTree<U extends PersistVersionableTree<U, T>, T extends PersistVersionData<U>> extends PersistVersionable<U, T> {

	void setParentItem(final T parentItem);

	T getParentItem();

	Set<T> getChildrenItems();

	void addChildItem(final T childItem);

	void removeChildItem(final T childItem);

}
