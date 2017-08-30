package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.AbstractPersistentKeyIdObject;
import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionData;
import com.github.bordertech.corpdir.jpa.common.feature.PersistentVersionable;
import com.sun.org.apache.xalan.internal.utils.Objects;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AbstractPersistentKeyIdDataVersionObject<T extends PersistentVersionable> extends AbstractPersistentKeyIdObject implements PersistentVersionData<T> {

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@MapsId(value = "id")
	private Set<T> dataVersions;

	/**
	 * Default constructor.
	 */
	protected AbstractPersistentKeyIdDataVersionObject() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public AbstractPersistentKeyIdDataVersionObject(final Long id) {
		super(id);
	}

	@Override
	public Set<T> getDataVersions() {
		return dataVersions;
	}

	@Override
	public void addDataVersion(final T versionData) {
		if (dataVersions == null) {
			dataVersions = new HashSet<>();
		}
		dataVersions.add(versionData);
	}

	@Override
	public void removeDataVersion(final Integer versionId) {
		if (this.dataVersions != null) {
			for (T link : this.dataVersions) {
				if (Objects.equals(link.getVersionId(), versionId)) {
					this.dataVersions.remove(link);
					if (this.dataVersions.isEmpty()) {
						this.dataVersions = null;
					}
					return;
				}
			}
		}
	}

	@Override
	public T getDataVersion(final Integer versionId) {
		if (this.dataVersions == null) {
			this.dataVersions = new HashSet<>();
		}
		for (T data : this.dataVersions) {
			if (Objects.equals(data.getVersionId(), versionId)) {
				return data;
			}
		}
		T data = createDataVersion(versionId);
		this.dataVersions.add(data);
		return data;
	}

	protected abstract T createDataVersion(final Integer versionId);

}
