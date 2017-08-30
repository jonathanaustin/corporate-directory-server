package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistentTreeable;
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
public class AbstractPersistentKeyIdTreeObject<T extends PersistentTreeable> extends AbstractPersistentKeyIdObject implements PersistentTreeable<T> {

	@ManyToOne
	@JoinColumn(name = "parentId")
	private T parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<T> children;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentKeyIdTreeObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public AbstractPersistentKeyIdTreeObject(final Long id) {
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
