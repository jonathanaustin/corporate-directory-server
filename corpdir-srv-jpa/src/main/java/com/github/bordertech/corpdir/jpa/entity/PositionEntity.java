package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdVersionObject;
import com.github.bordertech.corpdir.jpa.entity.links.PositionLinksEntity;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Position in organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "Position")
public class PositionEntity extends DefaultKeyIdVersionObject<PositionLinksEntity> {

	@ManyToOne
	private PositionTypeEntity type;

	/**
	 * Default constructor.
	 */
	protected PositionEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public PositionEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the position type
	 */
	public PositionTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the position type
	 */
	public void setType(final PositionTypeEntity type) {
		this.type = type;
	}

	@Override
	protected PositionLinksEntity createDataVersion(final Long versionId) {
		return new PositionLinksEntity(versionId, this);
	}

}
