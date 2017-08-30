package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Persistent Object with a Tree Structure.
 *
 * @author jonathan
 * @param <T> the item id types
 */
public interface PersistentVersionableTree<U extends PersistentVersionableTree, T extends PersistentVersionData<U>> extends PersistentVersionable<T> {

	void setParent(final T parent);

	T getParent();

	Set<T> getChildren();

	void addChild(final T child);

	void removeChild(final T child);

}
