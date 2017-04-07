package com.github.bordertech.corpdir.jpa.common;

import javax.persistence.MappedSuperclass;

/**
 * Abstract nested set (ie tree) keyed object.
 *
 * @param <T> the nested set entity
 * @author jonathan
 */
@MappedSuperclass
public abstract class AbstractPersistentNestedObject<T extends PersistentNestedObject> extends AbstractPersistentTreeObject<T> implements PersistentNestedObject<T> {

	private Long threadId; // null on root
	private Long leftIdx;
	private Long rightIdx;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentNestedObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public AbstractPersistentNestedObject(final Long id) {
		super(id);
	}

	@Override
	public Long getParentId() {
		return getParent() == null ? null : getParent().getId();
	}

	@Override
	public Long getThreadId() {
		return threadId;
	}

	@Override
	public void setThreadId(final Long threadId) {
		this.threadId = threadId;
	}

	@Override
	public Long getLeftIdx() {
		return leftIdx;
	}

	@Override
	public void setLeftIdx(final Long leftIdx) {
		this.leftIdx = leftIdx;
	}

	@Override
	public Long getRightIdx() {
		return rightIdx;
	}

	@Override
	public void setRightIdx(final Long rightIdx) {
		this.rightIdx = rightIdx;
	}

}
