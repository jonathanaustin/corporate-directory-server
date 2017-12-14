package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyId;
import com.github.bordertech.corpdir.jpa.common.version.ItemVersion;
import com.github.bordertech.corpdir.jpa.entity.VersionCtrlEntity;
import java.util.Objects;
import java.util.Set;
import javax.persistence.MappedSuperclass;

/**
 * Default keyed object with version data.
 *
 * @author Jonathan Austin
 * @param <T> the persist type
 * @param <V> the version data type
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class DefaultVersionableKeyIdObject<T extends PersistVersionableKeyId<T, V>, V extends ItemVersion<T>> extends DefaultKeyIdObject implements PersistVersionableKeyId<T, V> {

	private String businessKey;

	/**
	 * Default constructor.
	 */
	protected DefaultVersionableKeyIdObject() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public DefaultVersionableKeyIdObject(final Long id) {
		super(id);
	}

	@Override
	public void addVersion(final V versionData) {
		getVersions().add(versionData);
	}

	@Override
	public void removeVersion(final Long versionId) {
		Set<V> dataVersions = getVersions();
		if (dataVersions != null) {
			for (V link : dataVersions) {
				if (Objects.equals(link.getVersionId(), versionId)) {
					dataVersions.remove(link);
					return;
				}
			}
		}
	}

	@Override
	public V getVersion(final Long versionId) {
		Set<V> dataVersions = getVersions();
		if (dataVersions != null) {
			for (V data : dataVersions) {
				if (Objects.equals(data.getVersionId(), versionId)) {
					return data;
				}
			}
		}
		return null;
	}

	@Override
	public String getBusinessKey() {
		return businessKey;
	}

	@Override
	public void setBusinessKey(final String businessKey) {
		this.businessKey = businessKey;
	}

	@Override
	public V getOrCreateVersion(final VersionCtrlEntity ctrl) {
		V links = getVersion(ctrl.getId());
		if (links == null) {
			links = createVersion(ctrl);
			addVersion(links);
		}
		return links;
	}

	@Override
	public V createVersion(final VersionCtrlEntity ctrl) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
