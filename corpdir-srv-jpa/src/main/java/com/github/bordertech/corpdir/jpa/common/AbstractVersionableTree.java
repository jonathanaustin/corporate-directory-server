package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.*;
import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionableTree;
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
public class AbstractVersionableTree<U extends PersistentVersionableTree, T extends PersistentVersionData<U>> extends AbstractVersionable<T> implements PersistentVersionableTree<U, T> {

	@ManyToOne
	@JoinColumn(name = "parentId")
	private T parent;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
	private Set<T> children;

	public AbstractVersionableTree() {
	}

	/**
	 * @param versionId the tree version id
	 * @param item the owner item
	 */
	public AbstractVersionableTree(final Integer versionId, final T item) {
		super(versionId, item);
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
		child.getDataVersion(getVersionId()).setParent(getItem());
	}

	@Override
	public void removeChild(final T child) {
		if (children != null) {
			children.remove(child);
		}
		child.getDataVersion(getVersionId()).setParent(null);
	}

}
