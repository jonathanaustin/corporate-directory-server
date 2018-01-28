package com.github.bordertech.corpdir.jpa.common.feature;

import java.util.Set;

/**
 * Persistent Keyed Object with a Tree Structure.
 *
 * @author jonathan
 * @param <T> the item id types
 */
public interface PersistKeyIdTree<T extends PersistKeyIdTree> extends PersistKeyIdObject {

	void setParent(final T parent);

	T getParent();

	Set<T> getChildren();

	void addChild(final T child);

	void removeChild(final T child);

}
