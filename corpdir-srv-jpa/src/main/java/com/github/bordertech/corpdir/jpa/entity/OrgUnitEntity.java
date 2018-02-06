package com.github.bordertech.corpdir.jpa.entity;

import com.github.bordertech.corpdir.jpa.common.DefaultVersionableKeyIdTreeObject;
import com.github.bordertech.corpdir.jpa.entity.version.OrgUnitVersionEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Organization unit.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
@Entity
@Table(name = "OrgUnit")
public class OrgUnitEntity extends DefaultVersionableKeyIdTreeObject<OrgUnitEntity, OrgUnitVersionEntity> {

	@OneToMany(mappedBy = "item", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<OrgUnitVersionEntity> versions;

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
	public OrgUnitVersionEntity createVersion(final VersionCtrlEntity ctrl) {
		return new OrgUnitVersionEntity(ctrl, this);
	}

	@Override
	public Set<OrgUnitVersionEntity> getVersions() {
		if (versions == null) {
			versions = new HashSet<>();
		}
		return versions;
	}

}
