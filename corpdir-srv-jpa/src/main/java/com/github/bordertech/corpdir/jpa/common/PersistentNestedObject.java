package com.github.bordertech.corpdir.jpa.common;

public interface PersistentNestedObject<T extends PersistentNestedObject> extends PersistentTreeObject<T> {

	Long getParentId();

	Long getThreadId();

	void setThreadId(final Long threadId);

	Long getLeftIdx();

	void setLeftIdx(final Long left);

	Long getRightIdx();

	void setRightIdx(final Long right);

}
