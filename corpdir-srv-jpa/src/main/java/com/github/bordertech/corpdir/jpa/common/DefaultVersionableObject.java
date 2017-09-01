package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.VersionIdKey;
import java.sql.Timestamp;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Default versionable data holder.
 *
 * @author jonathan
 */
@MappedSuperclass
public class DefaultVersionableObject<U extends PersistVersionable<U, T>, T extends PersistVersionData<U>> implements PersistVersionable<U, T> {

	@EmbeddedId
	private VersionIdKey versionIdKey;

	@ManyToOne
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
	public DefaultVersionableObject(final Integer versionId, final T item) {
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
	public Integer getVersionId() {
		return versionIdKey == null ? null : versionIdKey.getVersionId();
	}

	@Override
	public VersionIdKey getVersionIdKey() {
		return versionIdKey;
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
