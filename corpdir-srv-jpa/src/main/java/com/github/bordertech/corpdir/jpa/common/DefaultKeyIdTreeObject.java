package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistKeyIdTree;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Default keyed object with a tree structure.
 *
 * @param <T> the tree entity
 * @author jonathan
 */
@MappedSuperclass
public class DefaultKeyIdTreeObject<T extends PersistKeyIdTree> extends DefaultKeyIdObject implements PersistKeyIdTree<T> {

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "parentId")
	private T parent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.MERGE)
	private Set<T> children;

	/**
	 * Default constructor.
	 */
	protected DefaultKeyIdTreeObject() {
		// Default constructor
	}

	/**
	 * @param id the entity id
	 */
	public DefaultKeyIdTreeObject(final Long id) {
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
		// Bi-Directional
		child.setParent(this);
	}

	@Override
	public void removeChild(final T child) {
		if (children != null) {
			children.remove(child);
		}
		// Bi-Directional
		child.setParent(null);
	}

}
