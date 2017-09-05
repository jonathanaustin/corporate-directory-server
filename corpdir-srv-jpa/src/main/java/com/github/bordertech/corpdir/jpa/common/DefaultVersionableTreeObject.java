package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableTree;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Default versionable data holder with tree structure.
 *
 * @param <U> the versionable tree data type
 * @param <T> the version data owner type
 * @author jonathan
 */
@MappedSuperclass
public class DefaultVersionableTreeObject<U extends PersistVersionableTree<U, T>, T extends PersistVersionData<U>> extends DefaultVersionableObject<U, T> implements PersistVersionableTree<U, T> {

	@ManyToOne
	private T parentItem;

	@ManyToMany(fetch = FetchType.LAZY)
	private Set<T> childrenItems;

	protected DefaultVersionableTreeObject() {
	}

	/**
	 * @param versionCtrl the tree version id
	 * @param item the owner item id
	 */
	public DefaultVersionableTreeObject(final VersionCtrlEntity versionCtrl, final T item) {
		super(versionCtrl, item);
	}

	@Override
	public T getParentItem() {
		return parentItem;
	}

	@Override
	public void setParentItem(final T parentItem) {
		this.parentItem = parentItem;
	}

	@Override
	public Set<T> getChildrenItems() {
		return childrenItems;
	}

	@Override
	public void addChildItem(final T child) {
		if (childrenItems == null) {
			childrenItems = new HashSet<>();
		}
		childrenItems.add(child);
		child.getOrCreateDataVersion(getVersionCtrl()).setParentItem(getItem());
	}

	@Override
	public void removeChildItem(final T child) {
		if (childrenItems != null) {
			childrenItems.remove(child);
		}
		child.getOrCreateDataVersion(getVersionCtrl()).setParentItem(null);
	}

}
