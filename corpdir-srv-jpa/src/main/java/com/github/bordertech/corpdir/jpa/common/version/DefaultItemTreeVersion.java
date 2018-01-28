package com.github.bordertech.corpdir.jpa.common.version;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * Default versionable data holder with tree structure.
 *
 * @param <T> the version data owner type
 * @param <V> the version data type
 * @author jonathan
 */
@MappedSuperclass
public class DefaultItemTreeVersion<T extends PersistVersionableKeyId<T, V>, V extends ItemTreeVersion<T, V>> extends DefaultItemVersion<T, V> implements ItemTreeVersion<T, V> {

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumns({
		@JoinColumn(referencedColumnName = "item_id", name = "parent_item_id")
		, @JoinColumn(referencedColumnName = "versionCtrl_id", name = "parent_versionCtrl_id")
	})
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
		V vers = getParentVersionItem();
		return vers == null ? null : vers.getItem();
	}

	@Override
	public void setParentItem(final T item) {
		// Bi Directional
		if (item == null) {
			parentVersionItem = null;
			return;
		}
		V vers = item.getOrCreateVersion(getVersionCtrl());
		parentVersionItem = vers;
		// To stop a circular call only add if not there
		if (!vers.getChildrenItems().contains(getItem())) {
			vers.addChildItem(getItem());
		}
	}

	@Override
	public Set<T> getChildrenItems() {
		return extractItems(getChildVersionItems());
	}

	@Override
	public void addChildItem(final T item) {
		if (item == null) {
			return;
		}
		// Add Child
		V vers = item.getOrCreateVersion(getVersionCtrl());
		getChildVersionItems().add(vers);
		// Bi-Directional
		vers.setParentItem(getItem());
	}

	@Override
	public void removeChildItem(final T item) {
		if (item == null) {
			return;
		}
		// Remove Child
		V vers = item.getOrCreateVersion(getVersionCtrl());
		getChildVersionItems().remove(vers);
		// Bi-Directional
		vers.setParentItem(null);
	}

	protected V getParentVersionItem() {
		return parentVersionItem;
	}

	protected Set<V> getChildVersionItems() {
		if (childVersionItems == null) {
			childVersionItems = new HashSet<>();
		}
		return childVersionItems;
	}
}
