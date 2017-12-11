package com.github.bordertech.corpdir.jpa.common.version;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Default versionable data holder with tree structure.
 *
 * @param <T> the version data owner type
 * @author jonathan
 */
@MappedSuperclass
public class DefaultItemTreeVersion<T extends PersistVersionableKeyId<T, V>, V extends ItemTreeVersion<T>> extends DefaultItemVersion<T, V> implements ItemTreeVersion<T> {

	@ManyToOne(cascade = CascadeType.MERGE)
//	@JoinColumn(name = "parentId")
	private V parentVersionItem;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentVersionItem", cascade = CascadeType.MERGE)
	private Set<V> childVersionItems;

	protected DefaultItemTreeVersion() {
	}

	/**
	 * @param versionCtrl the tree version id
	 * @param item the owner item id
	 */
	public DefaultItemTreeVersion(final VersionCtrlEntity versionCtrl, final T item) {
		super(versionCtrl, item);
	}

	@Override
	public T getParentItem() {
		return parentVersionItem == null ? null : parentVersionItem.getItem();
	}

	@Override
	public void setParentItem(final T item) {
		this.parentVersionItem = item.getOrCreateVersion(getVersionCtrl());
		// Bi Directional
		parentVersionItem.addChildItem(item);
	}

	@Override
	public Set<T> getChildrenItems() {
		return extractItems(childVersionItems);
	}

	@Override
	public void addChildItem(final T item) {
		if (childVersionItems == null) {
			childVersionItems = new HashSet<>();
		}
		V vers = item.getOrCreateVersion(getVersionCtrl());
		childVersionItems.add(vers);
		// Bi-Directional
		vers.setParentItem(getItem());
	}

	@Override
	public void removeChildItem(final T item) {

		if (childVersionItems != null) {
			for (V vers : childVersionItems) {
				if (item == vers.getItem()) {
					childVersionItems.remove(vers);
					// Bi-Directional
					vers.setParentItem(null);
					break;
				}
			}
		}

	}

}
