package com.github.bordertech.corpdir.jpa.common.version;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.Version;

/**
 * Default versionable data holder.
 *
 * @author jonathan
 * @param <T> the version data owner type
 */
@MappedSuperclass
public class DefaultItemVersion<T extends PersistVersionableKeyId<T, V>, V extends ItemVersion<T>> implements ItemVersion<T> {

	@EmbeddedId
	private VersionKey versionIdKey;

	private String description;

	@ManyToOne
	@MapsId(value = "versionId")
	private VersionCtrlEntity versionCtrl;

	@ManyToOne
	@MapsId(value = "id")
	private T item;

	@Version
	private Timestamp version;

	/**
	 * Default constructor.
	 */
	protected DefaultItemVersion() {
		// Default constructor
	}

	/**
	 * @param versionCtrl the version ctrl item
	 * @param item the owner item
	 */
	public DefaultItemVersion(final VersionCtrlEntity versionCtrl, final T item) {
		this.versionIdKey = new VersionKey(versionCtrl.getId(), item.getId());
		this.versionCtrl = versionCtrl;
		this.item = item;
	}

	@Override
	public T getItem() {
		return item;
	}

	@Override
	public Long getId() {
		return versionIdKey == null ? null : versionIdKey.getId();
	}

	@Override
	public Long getVersionId() {
		return versionIdKey == null ? null : versionIdKey.getVersionId();
	}

	@Override
	public VersionCtrlEntity getVersionCtrl() {
		return versionCtrl;
	}

	@Override
	public VersionKey getVersionKey() {
		return versionIdKey;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public Timestamp getTimestamp() {
		return version;
	}

	@Override
	public void setTimestamp(final Timestamp version) {
		this.version = version;
	}

	public static <V extends ItemVersion<T>, T extends PersistVersionableKeyId<T, V>> Set<T> extractItems(final Set<V> items) {
		Set<T> children = new HashSet<>();
		if (items != null) {
			for (V child : items) {
				children.add(child.getItem());
			}
		}
		return children;
	}

}
