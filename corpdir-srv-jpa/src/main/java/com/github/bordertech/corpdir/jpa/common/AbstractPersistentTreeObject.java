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
 * @param <T> the tree entity
 * @author jonathan
 */
@MappedSuperclass
public abstract class AbstractPersistentTreeObject<T extends PersistentTreeObject> extends AbstractPersistentKeyIdObject implements PersistentTreeObject<T> {

	@ManyToOne
	@JoinColumn(name = "parentId")
	private T parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<T> children;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentTreeObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public AbstractPersistentTreeObject(final Long id) {
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
