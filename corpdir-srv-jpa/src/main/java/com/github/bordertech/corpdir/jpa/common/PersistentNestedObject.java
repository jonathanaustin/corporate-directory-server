package com.github.bordertech.corpdir.jpa.common;

import java.util.Set;

public interface PersistentNestedObject<T extends PersistentNestedObject> extends PersistentKeyIdObject {

	void setParent(final T parent);

	T getParent();

	Long getParentId();

	Long getThreadId();

	void setThreadId(final Long threadId);

	Long getLeftIdx();

	void setLeftIdx(final Long left);

	Long getRightIdx();

	void setRightIdx(final Long right);

	Set<T> getChildren();

	void addChild(final T child);

	void removeChild(final T child);
}
