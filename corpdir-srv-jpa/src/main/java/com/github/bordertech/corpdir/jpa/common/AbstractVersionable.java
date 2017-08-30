package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionable;
import com.github.bordertech.corpdir.jpa.common.feature.VersionIdKey;
import java.sql.Timestamp;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Abstract version holder.
 *
 * @author jonathan
 * @param <T> the owner type
 */
@MappedSuperclass
public class AbstractVersionable<T extends PersistentVersionData> implements PersistentVersionable<T> {

	@EmbeddedId
	private VersionIdKey versionIdKey;

	private T item;

	@Version
	private Timestamp version;

	/**
	 * Default constructor.
	 */
	protected AbstractVersionable() {
		// Default constructor
	}

	/**
	 * @param versionId the tree version id
	 * @param item the owner item
	 */
	public AbstractVersionable(final Integer versionId, final T item) {
		this.versionIdKey = new VersionIdKey(versionId, item.getId());
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
