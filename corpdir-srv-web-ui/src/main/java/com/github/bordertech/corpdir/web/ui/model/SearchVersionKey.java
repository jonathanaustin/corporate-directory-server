package com.github.bordertech.corpdir.web.ui.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Keyed API Object with a Version.
 *
 * @author jonathan
 */
public class SearchVersionKey implements Serializable {

	private Long versionId;
	private String id;

	public SearchVersionKey() {
	}

	public SearchVersionKey(final Long versionId, final String id) {
		this.versionId = versionId;
		this.id = id;
	}

	public Long getVersionId() {
		return versionId;
	}

	public void setVersionId(final Long versionId) {
		this.versionId = versionId;
	}

	public String getId() {
		return id;
	}

	public void setId(final String id) {
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
		final SearchVersionKey other = (SearchVersionKey) obj;
		if (!Objects.equals(this.versionId, other.versionId)) {
			return false;
		}
		if (!Objects.equals(this.id, other.id)) {
			return false;
		}
		return true;
	}

}
