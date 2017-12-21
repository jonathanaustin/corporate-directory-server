package com.github.bordertech.corpdir.jpa.common.version;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 * API Object with a Version.
 *
 * @author jonathan
 */
@Embeddable
public class VersionKey implements Serializable {

	private Long versionId;
	private Long id;

	public VersionKey() {
	}

	public VersionKey(final Long versionId, final Long id) {
		this.versionId = versionId;
		this.id = id;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(final Long versionId) {
		this.versionId = versionId;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.versionId);
		hash = 89 * hash + Objects.hashCode(this.id);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VersionKey other = (VersionKey) obj;
		if (!Objects.equals(this.versionId, other.versionId)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
