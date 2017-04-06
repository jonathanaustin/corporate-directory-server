package com.github.bordertech.corpdir.jpa.common;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Abstract nested set (ie tree) keyed object.
 *
 * @param <T> the nested set entity
 * @author jonathan
 */
@MappedSuperclass
public abstract class AbstractPersistentNestedSet<T extends PersistentNestedSet> extends AbstractPersistentKeyIdObject implements PersistentNestedSet<T> {

	@ManyToOne
	@JoinColumn(name = "parentId")
	private T parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<T> children;

	private Long threadId; // null on root
	private Long leftIdx;
	private Long rightIdx;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentNestedSet() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public AbstractPersistentNestedSet(final Long id) {
		super(id);
	}

	@Override
	public T getParent() {
		return parent;
	}

	@Override
	public void setParent(final T parent) {
		this.parent = parent;
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

	@Override
	public Set<T> getChildren() {
		return children;
	}

	@Override
	public void addChild(final T child) {
		if (children == null) {
			children = new HashSet<>();
		}
		children.add(child);
		child.setParent(this);
	}

	@Override
	public void removeChild(final T child) {
		if (children != null) {
			children.remove(child);
		}
		child.setParent(null);
	}

}
