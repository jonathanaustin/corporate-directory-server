package com.github.bordertech.corpdir.jpa.common.feature;

import com.github.bordertech.corpdir.jpa.common.PersistentObject;
import java.util.Set;

/**
 * Persistent Object with a Tree Structure.
 *
 * @author jonathan
 * @param <T> the item id types
 */
public interface PersistentTreeable<T extends PersistentTreeable> extends PersistentObject {

	void setParent(final T parent);

	T getParent();

	Set<T> getChildren();

	void addChild(final T child);

	void removeChild(final T child);

}
