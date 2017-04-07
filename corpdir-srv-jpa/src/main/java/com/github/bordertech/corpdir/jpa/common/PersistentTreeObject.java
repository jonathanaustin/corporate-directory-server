package com.github.bordertech.corpdir.jpa.common;

import java.util.Set;

public interface PersistentTreeObject<T extends PersistentTreeObject> extends PersistentKeyIdObject {

	void setParent(final T parent);

	T getParent();

	Set<T> getChildren();

	void addChild(final T child);

	void removeChild(final T child);
}
