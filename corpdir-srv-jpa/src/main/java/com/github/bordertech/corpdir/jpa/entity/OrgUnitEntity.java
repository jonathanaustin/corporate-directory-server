package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultKeyIdVersionObject;
import com.github.bordertech.corpdir.jpa.entity.links.OrgUnitLinks;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnit")
public class OrgUnitEntity extends DefaultKeyIdVersionObject<OrgUnitLinks> {

	@ManyToOne
	private UnitTypeEntity type;

	/**
	 * Default constructor.
	 */
	protected OrgUnitEntity() {
	}

	/**
	 *
	 * @param id the entity id
	 */
	public OrgUnitEntity(final Long id) {
		super(id);
	}

	/**
	 *
	 * @return the organization type
	 */
	public UnitTypeEntity getType() {
		return type;
	}

	/**
	 *
	 * @param type the organization type
	 */
	public void setType(final UnitTypeEntity type) {
		this.type = type;
	}

	@Override
	protected OrgUnitLinks createDataVersion(final Integer versionId) {
		return new OrgUnitLinks(versionId, this);
	}

}
