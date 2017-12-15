package com.github.bordertech.corpdir.jpa.common;

import com.github.bordertech.corpdir.jpa.common.feature.PersistVersionableKeyIdTree;
import com.github.bordertech.corpdir.jpa.common.version.ItemTreeVersion;
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
public abstract class DefaultVersionableKeyIdTreeObject<T extends PersistVersionableKeyIdTree<T, V>, V extends ItemTreeVersion<T, V>> extends DefaultVersionableKeyIdObject<T, V> implements PersistVersionableKeyIdTree<T, V> {

	/**
	 * Default constructor.
	 */
	protected DefaultVersionableKeyIdTreeObject() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public DefaultVersionableKeyIdTreeObject(final Long id) {
		super(id);
	}

}
