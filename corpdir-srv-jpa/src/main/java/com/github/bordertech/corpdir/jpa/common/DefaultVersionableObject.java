package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.VersionIdKey;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.sql.Timestamp;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.Version;

/**
 * Default versionable data holder.
 *
 * @author jonathan
 * @param <U> the versionable data type
 * @param <T> the version data owner type
 */
@MappedSuperclass
public class DefaultVersionableObject<U extends PersistVersionable<U, T>, T extends PersistVersionData<U>> implements PersistVersionable<U, T> {

	@EmbeddedId
	private VersionIdKey versionIdKey;

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
	protected DefaultVersionableObject() {
		// Default constructor
	}

	/**
	 * @param versionId the tree version id
	 * @param item the owner item id
	 */
	public DefaultVersionableObject(final Long versionId, final T item) {
		this.versionIdKey = new VersionIdKey(versionId, item.getId());
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
	public VersionIdKey getVersionIdKey() {
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

}
